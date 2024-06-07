package com.vineeth.movies.ui

import MovieListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vineeth.movies.adapters.ViewPagerAdapter
import com.vineeth.movies.databinding.ActivityMainBinding
import com.vineeth.movies.databinding.LayoutMovieDetailsBinding
import com.vineeth.movies.entitys.HomeData
import com.vineeth.movies.entitys.Movieslist
import com.vineeth.movies.interfaces.ApiCallListner
import com.vineeth.movies.interfaces.MovieClickListner
import com.vineeth.movies.ui.fragments.MovieLoadingfragment
import com.vineeth.movies.viewmodel.MoveListViewModel
import com.vineeth.movies.viewmodel.MoveListViewModelFactory

class MainActivity : AppCompatActivity(), ApiCallListner, MovieClickListner {
    private lateinit var moveListViewModel: MoveListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initValues()
        moveListViewModel.fetchingMoveList()
        moveListViewModel.allHomeData.observe(this@MainActivity, Observer {
            setupViewPager(binding.movieViewpager,it)

        })


    }

    override fun onStart() {
        super.onStart()
        moveListViewModel.fetchingMoveList()
    }

    private fun initValues() {
        val factory = MoveListViewModelFactory(applicationContext, this@MainActivity)
        moveListViewModel = ViewModelProvider(this, factory).get(modelClass = MoveListViewModel::class.java)
    }

    override fun onApiCallStart() {
//        TODO("Not yet implemented")
    }

    override fun onApiCallSuccess() {
//        TODO("Not yet implemented")
    }

    override fun onApiCallError() {
        Toast.makeText(this@MainActivity,"Network Error",Toast.LENGTH_LONG).show()
    }

    override fun onMovieClick(movie: Movieslist) {
        showMovieDialog(movie)
    }

    fun showMovieDialog(movie: Movieslist) {
        val dialog = BottomSheetDialog(this)
        var dialog_binding = LayoutMovieDetailsBinding.inflate(layoutInflater)
        dialog_binding.txtName.text = "Movie Name : ${movie.title}"
        dialog_binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(dialog_binding.root)
        dialog.show()
    }

    private fun setupViewPager(viewPager: ViewPager, datalist :List<HomeData>) {
        val adapter = ViewPagerAdapter(this.supportFragmentManager)
        adapter.addFragment(MovieLoadingfragment(HomeData(id = "0", genre ="All", movieslist = null, type = 0),viewModel = moveListViewModel,movieClickListner = this@MainActivity), "All")
        for(data in datalist){
            adapter.addFragment(MovieLoadingfragment(data, viewModel = moveListViewModel, movieClickListner = this@MainActivity), data.genre)
        }
        viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(viewPager)
    }
}