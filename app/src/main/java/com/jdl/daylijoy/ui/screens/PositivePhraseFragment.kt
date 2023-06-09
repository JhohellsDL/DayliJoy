package com.jdl.daylijoy.ui.screens

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.jdl.daylijoy.R
import com.jdl.daylijoy.data.providers.PhraseProvider
import com.jdl.daylijoy.data.repositories.PhraseRepository
import com.jdl.daylijoy.databinding.FragmentPositivePhraseBinding
import java.io.IOException
import java.io.OutputStream

class PositivePhraseFragment : Fragment() {

    private lateinit var binding: FragmentPositivePhraseBinding

    private val provider: PhraseRepository = PhraseRepository(PhraseProvider())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPositivePhraseBinding.inflate(inflater)

        binding.textPositivePhrase.text = provider.getRandomPositivePhrase()

        binding.cardViewShareImage.setOnClickListener {
            captureAndSaveImage()
        }
        binding.cardViewShareMessage.setOnClickListener{
            shareAppLink()
        }

        binding.botonRegresar.setOnClickListener {
            it.findNavController().navigate(R.id.action_positivePhraseFragment_to_sentencesFragment)
        }

        return binding.root
    }

    private fun captureAndSaveImage() {

        var message = "¡Excelente trabajo! Tu compromiso con el registro de cosas buenas te ayudará a apreciar las pequeñas alegrías de la vida. ¡Sigue así!\n\n"
        message += "https://play.google.com/store/apps/details?id=com.jdl.daylijoy"

        val screenshot = captureFragmentScreen(requireParentFragment())
        val imagePath = screenshot?.let {
            saveImage(it)
        }
        if (imagePath != null) {
            shareImage(imagePath, message)
        } else {
            Toast.makeText(requireContext(), " no hay captura!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun captureFragmentScreen(fragment: Fragment): Bitmap? {

        val view = fragment.view

        val bitmap =
            view?.let { Bitmap.createBitmap(it.width, view.height-500, Bitmap.Config.ARGB_8888) }

        val canvas = bitmap?.let { Canvas(it) }
        if (view != null) {
            view.draw(canvas)
            Log.i("asd", "-- si hay captura dentro!")
        } else {
            Log.i("asd", "-- no hay captura dentro!")
        }

        return bitmap
    }

    private fun saveImage(image: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "screenshot.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/MyAppScreenshots"
            )
        }

        val resolver = requireContext().contentResolver
        var outputStream: OutputStream? = null
        var imageUri: Uri?
        try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            imageUri = resolver.insert(contentUri, contentValues)
            Log.i("asd", "-- sse guardo! $imageUri")
            imageUri?.let {
                outputStream = resolver.openOutputStream(it)
                image.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                outputStream?.flush()
            }
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

        return imageUri
    }

    private fun shareImage(imagePath: Uri, message: String) {
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, imagePath)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(intent, "Compartir imagen"))
    }

    private fun shareAppLink() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartir")
            var message = "¡Echa un vistazo a esta increíble aplicación!\n\n"
            message += "https://play.google.com/store/apps/details?id=com.jdl.reegresionlinealapp"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, "Compartir en"))
        } catch (e: Exception) {
            // Manejo de errores
        }
    }

}