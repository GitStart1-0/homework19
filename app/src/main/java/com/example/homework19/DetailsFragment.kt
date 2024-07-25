package com.example.homework19

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class DetailsFragment : Fragment() {

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_IMAGE_URL = "imageUrl"
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(name: String, imageUrl: String, description: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putString(ARG_NAME, name)
            args.putString(ARG_IMAGE_URL, imageUrl)
            args.putString(ARG_DESCRIPTION, description)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)

        arguments?.getString(ARG_NAME)?.let { name ->
            textViewName.text = name
        }
        arguments?.getString(ARG_IMAGE_URL)?.let { imageUrl ->
            Glide.with(this).load(imageUrl).into(imageView)
        }
        arguments?.getString(ARG_DESCRIPTION)?.let { description ->
            textViewDescription.text = description
        }
    }
}
