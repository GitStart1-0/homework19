package com.example.homework19

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListFragment : Fragment() {
    private val disposable = CompositeDisposable()
    private var adapter: RecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        val request = ApiClient.retrofit.create(ApiInterface::class.java)
            .getSuperheroes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                adapter = RecyclerViewAdapter(response.toMutableList()) { superhero ->
                    onItemClicked(superhero)
                }
                recyclerView.adapter = adapter
            }, { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            })
        disposable.add(request)
    }

    private fun onItemClicked(superhero: Superhero) {
        val detailsFragment = DetailsFragment.newInstance(superhero.name, superhero.images.lg, "Extended description about ${superhero.name}")
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
