package com.vineeth.movies.ui.fragments

import MovieListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.vineeth.movies.databinding.FragmentMovieLoadingfragmentBinding

import com.vineeth.movies.entitys.HomeData
import com.vineeth.movies.interfaces.MovieClickListner
import com.vineeth.movies.viewmodel.MoveListViewModel


class MovieLoadingfragment(homeData: HomeData, viewModel: MoveListViewModel,movieClickListner: MovieClickListner) : Fragment() {

    private var _binding: FragmentMovieLoadingfragmentBinding? = null
    private val requestdata = homeData
    private val viewModel_ = viewModel
    private val movieClickListnerF=movieClickListner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieLoadingfragmentBinding.inflate(inflater, container, false)
        val view = _binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            viewModel_.getMovieListByTypeID(requestdata.id).observe(act, Observer {movielist->
                val movieAdapter = context?.let { it1 -> MovieListAdapter(it1) }
                _binding!!.movieRecycler.adapter = movieAdapter
                movieAdapter?.setData(movielist)
                movieAdapter?.setListner(movieClickListnerF)
            })
        }
    }

}