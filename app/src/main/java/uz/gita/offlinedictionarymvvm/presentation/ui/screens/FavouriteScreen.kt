package uz.gita.offlinedictionarymvvm.presentation.ui.screens

import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.offlinedictionarymvvm.R
import uz.gita.offlinedictionarymvvm.databinding.ScreenFavoriteBinding
import uz.gita.offlinedictionarymvvm.presentation.ui.adapter.FavouriteAdapter
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.FavoriteViewModel
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.impl.FavoriteViewModelImpl

class FavouriteScreen : Fragment(R.layout.screen_favorite) {
    private val binding by viewBinding(ScreenFavoriteBinding::bind)
    private val viewModel: FavoriteViewModel by viewModels<FavoriteViewModelImpl>()
    private val adapter = FavouriteAdapter()
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private var query: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.backButtonLiveData.observe(viewLifecycleOwner, backButtonObserver)
        viewModel.allFavouriteWordsByIdLiveData.observe(viewLifecycleOwner, allFavouriteWordsByIdObserver)
        viewModel.searchQueryLiveData.observe(viewLifecycleOwner, searchQueryObserver)
        viewModel.favouriteLiveData.observe(viewLifecycleOwner, favouriteObserver)
        viewModel.noResultLiveData.observe(viewLifecycleOwner, noResultObserver)
        viewModel.haveResultLiveData.observe(viewLifecycleOwner, haveResultObserver)

        binding.backButton.setOnClickListener {
            viewModel.onClickBackButton()
        }

        adapter.setOnClickRememberListener { _id ->
            viewModel.setOnClickFavourite(_id, query)
        }

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                handler.removeCallbacksAndMessages(null)
                viewModel.onClickSearch(p0)
                adapter.query = p0
                query = p0
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                handler.postDelayed(
                    {
                        adapter.query = p0
                        viewModel.onClickSearch(p0)
                        query = p0
                    },
                    200
                )
                return true
            }
        })

        val closeButton: ImageView =
            binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        closeButton.setOnClickListener {
            viewModel.loadWordsById()
            binding.searchView.setQuery("", false)
        }
    }

    private val backButtonObserver = Observer<Unit> {
        findNavController().popBackStack()
    }
    private val allFavouriteWordsByIdObserver = Observer<Cursor> {
        adapter.cursor = it
        binding.dictionaryList.adapter = adapter
        binding.dictionaryList.layoutManager = LinearLayoutManager(requireContext())
        binding.dictionaryList.itemAnimator = null
        Log.d("TTT", "$it")
    }

    private val searchQueryObserver = Observer<Cursor> {
        adapter.cursor = it
        binding.dictionaryList.adapter = adapter
        binding.dictionaryList.itemAnimator = null
    }

    private val favouriteObserver = Observer<Cursor> {
        adapter.cursor = it
        adapter.notifyDataSetChanged()
//        adapter.notifyItemRangeChanged(0, it.count)
        binding.dictionaryList.itemAnimator = null
//        adapter.notifyDataSetChanged()
//        binding.dictionaryList.adapter = adapter
    }
    private val noResultObserver = Observer<Unit> {
        binding.placeHolder.visibility = View.VISIBLE
    }
    private val haveResultObserver = Observer<Unit> {
        binding.placeHolder.visibility = View.GONE
    }
    private val notFoundDataFoundObserver = Observer<Unit> {
        binding.placeHolder.visibility = View.VISIBLE
    }
    private val foundDataObserver = Observer<Unit>{
        binding.placeHolder.visibility = View.GONE
    }
}