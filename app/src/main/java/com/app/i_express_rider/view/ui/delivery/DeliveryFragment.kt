package com.app.i_express_rider.view.ui.delivery

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.FragmentDeliveryBinding
import com.app.i_express_rider.view.adapter.AdapterParcel
import com.app.i_express_rider.view.utils.Constant
import com.app.i_express_rider.viewmodel.DataViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.io.IOException
import java.util.*


class DeliveryFragment : Fragment() ,OnMapReadyCallback  {

    private val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445

    private var googleMap: GoogleMap? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var currentLocationMarker: Marker? = null
    private var currentLocation: Location? = null
    private var firstTimeFlag = true


    private lateinit var dataViewModel: DataViewModel
    private var _binding: FragmentDeliveryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var placesClient: PlacesClient? = null

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if (locationResult.lastLocation == null) return
            currentLocation = locationResult.lastLocation
            if (firstTimeFlag && googleMap != null) {
                animateCamera(currentLocation!!)
                firstTimeFlag = false
            }
            showMarker(currentLocation!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataViewModel =
            ViewModelProvider(this).get(DataViewModel::class.java)
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
          1
        )

        val mapMessageLocation =  childFragmentManager.findFragmentById(com.app.i_express_rider.R.id.map) as SupportMapFragment
        mapMessageLocation.getMapAsync(this);

        if (!Places.isInitialized()) {
            Places.initialize(requireContext().applicationContext, getString(R.string.my_api_key), Locale.US);
        }
        val autocompleteFragmentSource =
            childFragmentManager.findFragmentById(com.app.i_express_rider.R.id.place_autocomplete_fragment_source)
             as AutocompleteSupportFragment

        val autocompleteFragmentDestination = childFragmentManager
            .findFragmentById(com.app.i_express_rider.R.id.place_autocomplete_fragment_destination)
                as AutocompleteSupportFragment

        autocompleteFragmentSource.setTypeFilter(TypeFilter.CITIES)

        autocompleteFragmentSource.setHint(" Source Location ")

        autocompleteFragmentSource.setPlaceFields(
            Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS
            )
        )


        autocompleteFragmentSource.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show()
                val photoRequest = FetchPhotoRequest.builder(
                    Objects.requireNonNull(place.getPhotoMetadatas()).get(0)
                )
                    .build()

            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })


        val adapterParcel = AdapterParcel(requireActivity(), AdapterParcel.ORIENTATION.HORIZONTAL)
        binding.rvAcceptedParcel.adapter = adapterParcel
        binding.rvAcceptedParcel.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(binding.rvAcceptedParcel)

        binding.fabNewParcel.setOnClickListener {
            val intent = Intent(context, ParcelListActivity::class.java)
            intent.putExtra(Constant.ParcelListModeKey, Constant.ParcelListMode_NEW)
            startActivity(intent)
        }

        binding.fabShowAsList.setOnClickListener {
            val intent = Intent(context, ParcelListActivity::class.java)
            intent.putExtra(Constant.ParcelListModeKey, Constant.ParcelListMode_ACEEPTED)
            startActivity(intent)
        }
        _binding?.currentLocationImageButton?.setOnClickListener(View.OnClickListener {
            if(googleMap != null && currentLocation != null){
                animateCamera(currentLocation!!)
            }
        })
    }

    private fun loadPermissions(perm: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(requireContext(), perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), perm)) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(perm), requestCode)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun getLocationFromAddressSource(context: Context, strAddress: String): LatLng? {
        val coder = Geocoder(context, Locale.getDefault())
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 50)
            if (address == null) {
                return null
            }
            if (address != null && address.size > 0) {
                for (location in address) {
                    location.latitude
                    location.longitude
                    p1 = LatLng(location.latitude, location.longitude)
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return p1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap;
    }

    private fun startCurrentLocationUpdates() {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(3000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )
                return
            }
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(requireContext())
        if (ConnectionResult.SUCCESS == status) return true else {
            if (googleApiAvailability.isUserResolvableError(status)) Toast.makeText(
                requireContext(),
                "Please Install google play services to use this application",
                Toast.LENGTH_LONG
            ).show()
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) Toast.makeText(
                requireContext(),
                "Permission denied by uses",
                Toast.LENGTH_SHORT
            )
                .show() else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) startCurrentLocationUpdates()
        }
    }

    private fun animateCamera(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        googleMap!!.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                getCameraPositionWithBearing(
                    latLng
                )
            )
        )
    }

    private fun getCameraPositionWithBearing(latLng: LatLng): CameraPosition {
        return CameraPosition.Builder().target(latLng).zoom(16f).build()
    }

    private fun showMarker(currentLocation: Location) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        if (currentLocationMarker == null) currentLocationMarker = googleMap!!.addMarker(
            MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker()).position(latLng)
        ) else MarkerAnimation.animateMarkerToGB(
            currentLocationMarker,
            latLng,
            LatLngInterPolator.Spherical()
        )
    }

    override fun onStop() {
        super.onStop()
        if (fusedLocationProviderClient != null) fusedLocationProviderClient!!.removeLocationUpdates(
            mLocationCallback
        )
    }

    override fun onResume() {
        super.onResume()
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            startCurrentLocationUpdates()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProviderClient = null
        googleMap = null
    }




}


