package com.example.daylijoy.ui.screens

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.daylijoy.R
import com.example.daylijoy.data.providers.PhraseProvider
import com.example.daylijoy.data.repositories.PhraseRepository
import com.example.daylijoy.databinding.FragmentPositivePhraseBinding
import java.io.File
import java.io.FileOutputStream

class PositivePhraseFragment : Fragment() {

    private lateinit var binding: FragmentPositivePhraseBinding

    private val provider: PhraseRepository = PhraseRepository(PhraseProvider())
    private lateinit var imageView: ImageView

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
            // Realizar captura de pantalla
            val screenshot = takeScreenshot()

            // Guardar la imagen en almacenamiento externo
            val imagePath = saveImage(screenshot)

            // Compartir la imagen
            shareImage(imagePath)
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

    private fun takeScreenshot(): Bitmap {
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
        /*val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, imagePath)
        startActivity(Intent.createChooser(intent, "Compartir imagen"))*/
    }

}