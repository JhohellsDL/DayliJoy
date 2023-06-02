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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jdl.daylijoy.R
import com.jdl.daylijoy.data.providers.PhraseProvider
import com.jdl.daylijoy.data.repositories.PhraseRepository
import com.jdl.daylijoy.databinding.FragmentPositivePhraseBinding
import java.io.IOException
import java.io.OutputStream

class PositivePhraseFragment : Fragment() {

    private lateinit var binding: FragmentPositivePhraseBinding

    private val provider: PhraseRepository = PhraseRepository(PhraseProvider())
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPositivePhraseBinding.inflate(inflater)


        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this.requireContext(),"ca-app-pub-8897050281816485/2544549811", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.toString()?.let { Log.d("asd", it) }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("asd", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })


        binding.textPositivePhrase.text = provider.getRandomPositivePhrase()

        binding.cardViewShareImage.setOnClickListener {
            captureAndSaveImage()
        }
        binding.cardViewShareMessage.setOnClickListener{
            shareAppLink()
        }

        binding.botonRegresar.setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
            it.findNavController().navigate(R.id.action_positivePhraseFragment_to_sentencesFragment)
        }

        return binding.root
    }

    private fun captureAndSaveImage() {

        var message = "¡Hoy tuve un día increíble! Registré 5 cosas buenas en mi app. #CosasBuenas #Positividad\n\n¡Echa un vistazo a esta increíble aplicación\n"
        message += "https://play.google.com/store/apps/details?id=com.jdl.daylijoy"

        val screenshot = captureFragmentScreen(requireParentFragment())
        val imagePath = screenshot?.let {
            saveImage(it)
        }
        if (imagePath != null) {
            shareImage(imagePath, message)
        } else {
            Toast.makeText(requireContext(), "Oops!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun captureFragmentScreen(fragment: Fragment): Bitmap? {

        val view = fragment.view

        val bitmap =
            view?.let { Bitmap.createBitmap(it.width, view.height-500, Bitmap.Config.ARGB_8888) }

        val canvas = bitmap?.let { Canvas(it) }
        if (view != null) {
            view.draw(canvas)
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