package com.app.i_express_rider.view.ui

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityAddRelativeBinding
import com.app.i_express_rider.databinding.ActivityAddressBinding
import com.app.i_express_rider.databinding.ActivityDocumentsAddBinding
import java.io.*

class DocumentsAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDocumentsAddBinding
    val TAKE_PICTURE_FROM_GALLERY_NATIONAL_ID = 1
    val TAKE_PICTURE_FROM_GALLERY_DRIVING_LICICENSE = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentsAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.finished.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.toolbar.setOnClickListener { finish() }

        binding.nationalID.setOnClickListener { selectImage() }

        binding.drivingLicense.setOnClickListener { selectImage() }
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
        when(requestCode){
            TAKE_PICTURE_FROM_GALLERY_NATIONAL_ID  ->{
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
                        val selectedImage: Uri = data?.data!!
                        val filePath = arrayOf(MediaStore.Images.Media.DATA)
                        val c: Cursor? = contentResolver.
                        query(selectedImage, filePath, null, null, null)
                        c?.moveToFirst()
                        val columnIndex: Int = c!!.getColumnIndex(filePath[0])
                        val picturePath: String = c.getString(columnIndex)
                        c.close()
                        val thumbnail = BitmapFactory.decodeFile(picturePath)
                        Log.w(
                            "path image from gallery",
                            picturePath + ""
                        )
                        binding.nationalID.setImageBitmap(thumbnail)
                    }


                }
            }
            TAKE_PICTURE_FROM_GALLERY_DRIVING_LICICENSE  ->{
                if (resultCode == RESULT_OK) {
                    if (requestCode == 1) {
                        var g = File(Environment.getExternalStorageDirectory().toString())
                        for (temp1 in g.listFiles()) {
                            if (temp1.getName().equals("temp1.jpg")) {
                                g = temp1
                                break
                            }
                        }
                        try {
                            val bitmap1: Bitmap
                            val bitmapOptions1 = BitmapFactory.Options()
                            bitmap1 = BitmapFactory.decodeFile(
                                g.getAbsolutePath(),
                                bitmapOptions1
                            )
                            binding.drivingLicense.setImageBitmap(bitmap1)
                            val path1: String = (Environment
                                .getExternalStorageDirectory()
                                    ).toString()+ File.separator+
                                    "Phoenix" + File.separator.toString() + "default"
                            g.delete()
                            var outFile1: OutputStream? = null
                            val file1 = File(path1, System.currentTimeMillis().toString() + ".jpg")
                            try {
                                outFile1 = FileOutputStream(file1)
                                bitmap1.compress(Bitmap.CompressFormat.JPEG, 85, outFile1)
                                outFile1.flush()
                                outFile1.close()
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
                        val selectedImage1: Uri = data?.data!!
                        val filePath1 = arrayOf(MediaStore.Images.Media.DATA)
                        val d: Cursor? = contentResolver.
                        query(selectedImage1, filePath1, null, null, null)
                        d?.moveToFirst()
                        val columnIndex1: Int = d!!.getColumnIndex(filePath1[0])
                        val picturePath1: String = d.getString(columnIndex1)
                        d.close()
                        val thumbnail1 = BitmapFactory.decodeFile(picturePath1)
                        Log.w(
                            "path image from gallery",
                            picturePath1 + ""
                        )
                        binding.drivingLicense.setImageBitmap(thumbnail1)
                    }


                }
            }


        }
    }
}