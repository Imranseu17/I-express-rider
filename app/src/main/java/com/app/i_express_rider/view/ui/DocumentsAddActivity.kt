package com.app.i_express_rider.view.ui

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.app.i_express_rider.databinding.ActivityDocumentsAddBinding
import java.io.*

class DocumentsAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDocumentsAddBinding
    val TAKE_PICTURE_FROM_GALLERY_NATIONAL_ID = 1
    val TAKE_PICTURE_FROM_GALLERY_DRIVING_LICICENSE = 2
    var currentImageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentsAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.finished.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.toolbar.setOnClickListener { finish() }

        binding.nationalID.setOnClickListener {
            currentImageView = it as ImageView?
            selectImage()
        }

        binding.drivingLicense.setOnClickListener {
            currentImageView = it as ImageView?
            selectImage() }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this@DocumentsAddActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val f = File(Environment.getExternalStorageDirectory(), "temp.jpg")
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f))
                startActivityForResult(intent, 1)
            } else if (options[item] == "Choose from Gallery") {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, 2)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
                if (resultCode == RESULT_OK) {
                    if (requestCode == 1) {
                        var f = File(Environment.getExternalStorageDirectory().toString())
                        for (temp in f.listFiles()) {
                            if (temp.getName().equals("temp.jpg")) {
                                f = temp
                                break
                            }
                        }
                        try {
                            val bitmap: Bitmap
                            val bitmapOptions = BitmapFactory.Options()
                            bitmap = BitmapFactory.decodeFile(
                                f.getAbsolutePath(),
                                bitmapOptions
                            )
                            binding.nationalID.setImageBitmap(bitmap)
                            val path: String = (Environment
                                .getExternalStorageDirectory()
                                    ).toString()+ File.separator+
                                    "Phoenix" + File.separator.toString() + "default"
                            f.delete()
                            var outFile: OutputStream? = null
                            val file = File(path, System.currentTimeMillis().toString() + ".jpg")
                            try {
                                outFile = FileOutputStream(file)
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile)
                                outFile.flush()
                                outFile.close()
                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else if (requestCode == 2) {
                        when(requestCode){
                            TAKE_PICTURE_FROM_GALLERY_NATIONAL_ID ->{
                                val selectedImage: Uri = data?.data!!
                                currentImageView?.setImageURI(selectedImage)
                            }
                            TAKE_PICTURE_FROM_GALLERY_DRIVING_LICICENSE ->{
                                val uri: Uri = data?.data!!
                                currentImageView?.setImageURI(uri)
                            }

                        }

                    }
        }
    }
}