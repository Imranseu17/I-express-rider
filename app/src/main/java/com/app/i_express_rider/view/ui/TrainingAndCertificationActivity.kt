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
import com.app.i_express_rider.databinding.ActivityEducationalBinding
import com.app.i_express_rider.databinding.ActivityTrainingAndCertificationBinding
import java.io.*

class TrainingAndCertificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainingAndCertificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainingAndCertificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.next.setOnClickListener {
            startActivity(
                Intent(this,
                WorkExperienceAddActivity::class.java)
            )
        }

        binding.certificate.setOnClickListener {
            selectImage()
        }

        binding.toolbar.setOnClickListener { finish() }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

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
                    binding.certificate.setImageBitmap(bitmap)
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
                val c: Cursor? = contentResolver.query(selectedImage, filePath, null, null, null)
                c?.moveToFirst()
                val columnIndex: Int = c!!.getColumnIndex(filePath[0])
                val picturePath: String = c.getString(columnIndex)
                c.close()
                val thumbnail = BitmapFactory.decodeFile(picturePath)
                Log.w(
                    "path image from gallery",
                    picturePath + ""
                )
                binding.certificate.setImageBitmap(thumbnail)
            }
        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this@TrainingAndCertificationActivity)
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
}