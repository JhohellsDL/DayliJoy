package com.example.daylijoy.ui.screens

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import android.Manifest
import androidx.core.app.ActivityCompat
import com.example.daylijoy.R
import com.example.daylijoy.data.providers.PhraseProvider
import com.example.daylijoy.data.repositories.PhraseRepository
import com.example.daylijoy.databinding.FragmentPositivePhraseBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PositivePhraseFragment : Fragment() {

    private lateinit var binding: FragmentPositivePhraseBinding

    private val provider: PhraseRepository = PhraseRepository(PhraseProvider())
    private lateinit var imageView: ImageView
    private val REQUEST_PERMISSION = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPositivePhraseBinding.inflate(inflater)

        binding.textPositivePhrase.text = provider.getRandomPositivePhrase()

        imageView = binding.imageView
        val imagePath = "/storage/self/primary/Pictures/.thumbnails/1000000002.jpg"

        /*// Cargar y mostrar la imagen utilizando Glide
        Glide.with(this)
            .load(File(imagePath))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imageView)*/


        binding.btnShare.setOnClickListener {
            val screenshot = takeScreenshot(binding.root)

            // Guardar la imagen en almacenamiento externo
            val imagePath = saveImage(screenshot)

            // Compartir la imagen
            shareImage(imagePath!!)

            // Guardar la imagen en almacenamiento externo

            /*val message = "¡Hoy tuve un día increíble! Registré 5 cosas buenas en mi app. #CosasBuenas #Positividad"

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, message)

            startActivity(Intent.createChooser(intent, "Compartir con"))*/
        }

        binding.botonRegresar.setOnClickListener {
            it.findNavController().navigate(R.id.action_positivePhraseFragment_to_sentencesFragment)
        }

        return binding.root
    }
    private fun isStoragePermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // User has already been asked for the permission and denied it.
                // Show a dialog to explain why the permission is needed.
            } else {
                // Request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION
                )
                return false
            }
        }
        return true
    }

    private fun takeScreenshot(view: View): Bitmap {
        val screenshot = view.getDrawingCache() ?: Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)
        val backgroundColor = view.background?.run { this } ?: Color.WHITE
        canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return screenshot
    }

    private fun saveImage(image: Bitmap): Uri? {
        val imagesFolder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyAppScreenshots")
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs()
        }

        val imageFile = File(imagesFolder, "screenshot.jpg")
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.flush()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return Uri.fromFile(imageFile)
    }

    private fun shareImage(imagePath: Uri) {
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, imagePath)
        startActivity(Intent.createChooser(intent, "Compartir imagen"))
    }

    /*private fun takeScreenshot(): Bitmap {
        val screenshot = view?.let { Bitmap.createBitmap(it.width, it.height, Bitmap.Config.ARGB_8888) }
        val canvas = Canvas(screenshot!!)
        //val backgroundColor = view?.background?.run { this } ?: Color.WHITE
        canvas.drawColor(Color.WHITE)
        view?.draw(canvas)
        return screenshot
    }

    private fun saveImage(image: Bitmap): String {
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile("screenshot", ".jpg", storageDir)
        val outputStream = FileOutputStream(imageFile)
        image.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        outputStream.close()
        // Cargar y mostrar la imagen utilizando Glide
        Glide.with(this)
            .load(File(imageFile.absolutePath))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imageView)
        Log.i("asd","-- ${imageFile.absolutePath}")
        return imageFile.absolutePath

    }

    private fun shareImage(imagePath: String) {
        val imageUri = Uri.parse(imagePath)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, imageUri)
        startActivity(Intent.createChooser(intent, "Compartir imagen"))
        *//*val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, imagePath)
        startActivity(Intent.createChooser(intent, "Compartir imagen"))*//*
    }*/

}