package com.jdll.daylijoy.ui.screens

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.daylijoy.R
import com.example.daylijoy.databinding.FragmentPositivePhraseBinding
import com.jdll.daylijoy.data.providers.PhraseProvider
import com.jdll.daylijoy.data.repositories.PhraseRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

        binding.cardViewShareImage.setOnClickListener {
            val screenshot = takeScreenshot(binding.root)
            val imagePath = saveImage(screenshot)
            shareImage(imagePath!!)
        }
        binding.cardViewShareMessage.setOnClickListener{
            shareAppLink()
        }

        binding.botonRegresar.setOnClickListener {
            it.findNavController().navigate(R.id.action_positivePhraseFragment_to_sentencesFragment)
        }

        return binding.root
    }

    private fun takeScreenshot(view: View): Bitmap {
        val screenshot = Bitmap.createBitmap(view.width, view.height-400, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)
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