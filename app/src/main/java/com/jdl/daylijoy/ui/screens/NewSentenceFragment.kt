package com.jdl.daylijoy.ui.screens

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.jdl.daylijoy.R
import com.jdl.daylijoy.databinding.FragmentNewSentenceBinding
import com.google.android.material.snackbar.Snackbar
import com.jdl.daylijoy.DailyJoyApplication
import com.jdl.daylijoy.data.entities.SentenceEntity
import com.jdl.daylijoy.ui.viewmodels.SentenceViewModel
import com.jdl.daylijoy.ui.viewmodels.SentenceViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewSentenceFragment : Fragment() {

    private lateinit var binding: FragmentNewSentenceBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewSentenceBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val sentenceViewModel: SentenceViewModel by viewModels {
            SentenceViewModelFactory((application as DailyJoyApplication).repository)
        }

        val dateFormat = getDate()

        val listEditText = listOf(
            binding.editSentence,
            binding.editSentence2,
            binding.editSentence3,
            binding.editSentence4,
            binding.editSentence5,
        )

        val listImageView = listOf(
            binding.imageGood1,
            binding.imageGood2,
            binding.imageGood3,
            binding.imageGood4,
            binding.imageGood5,
        )

        for (i in listEditText.indices) {
            setupEditText(
                editText = listEditText[i],
                image = listImageView[i]
            )
        }

        val action = R.id.action_newSentenceFragment_to_positivePhraseFragment

        binding.buttonSave.setOnClickListener {
            if (listEditText.any {
                    it.text.isNullOrBlank()
                }) {

                Snackbar.make(binding.root, getString(R.string.empty_not_saved), Toast.LENGTH_SHORT)
                    .setBackgroundTint(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorAccent
                        )
                    )
                    .show()
            } else {
                for (i in listEditText.indices) {
                    sentenceViewModel.insert(
                        SentenceEntity(
                            sentence = listEditText[i].text.toString(),
                            date = dateFormat
                        )
                    )
                }

                Snackbar.make(binding.root, getString(R.string.sentence_saved), Toast.LENGTH_SHORT)
                    .setBackgroundTint(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorSecondary
                        )
                    )
                    .show()
                it.findNavController().navigate(action)
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(): String {
        val date = LocalDate.now()
        val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return date.format(format)
    }

    private fun setupEditText(editText: EditText, image: ImageView) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val isTextEmpty = s.isNullOrBlank()
                binding.buttonSave.isEnabled = !isTextEmpty
                image.visibility = View.VISIBLE
            }
        })
    }

}