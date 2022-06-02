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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.offlinedictionarymvvm.R
import uz.gita.offlinedictionarymvvm.databinding.ScreenDictionaryBinding
import uz.gita.offlinedictionarymvvm.presentation.ui.adapter.SearchAdapter
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.DictionaryViewModel
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.impl.DictionaryViewModelImpl

class DictionaryScreen : Fragment(R.layout.screen_dictionary) {
    private val binding by viewBinding(ScreenDictionaryBinding::bind)
    private val viewModel: DictionaryViewModel by viewModels<DictionaryViewModelImpl>()
    private val adapter = SearchAdapter()
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private val args: DictionaryScreenArgs by navArgs()
    private var query:  String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadWordsById(args.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.backButtonLiveData.observe(viewLifecycleOwner, backButtonObserver)
        viewModel.allWordsByIdLiveData.observe(viewLifecycleOwner, allWordsByIdObserver)
        viewModel.searchQueryLiveData.observe(viewLifecycleOwner, searchQueryObserver)
        viewModel.favouriteLiveData.observe(viewLifecycleOwner, favouriteObserver)
        viewModel.noResultLiveData.observe(viewLifecycleOwner, noResultObserver)
        viewModel.haveResultLiveData.observe(viewLifecycleOwner, haveResultObserver)

        binding.categoryName.text = args.title

        binding.backButton.setOnClickListener {
            viewModel.onClickBackButton()
        }

        adapter.setOnClickRememberListener { _id, state, id_cat ->
            viewModel.setOnClickFavourite(state, _id, id_cat, query)
        }

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                handler.removeCallbacksAndMessages(null)
                viewModel.onClickSearch(args.id, p0)
                adapter.query = p0
                query = p0
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                handler.postDelayed(
                    {
                        adapter.query = p0
                        viewModel.onClickSearch(args.id, p0)
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
            viewModel.loadWordsById(args.id)
            binding.searchView.setQuery("", false)
        }
    }

    /**------------------------------------------------------------------*/

    private val backButtonObserver = Observer<Unit> {
        findNavController().popBackStack()
    }
    private val allWordsByIdObserver = Observer<Cursor> {
        adapter.cursor = it
        binding.dictionaryList.adapter = adapter
        binding.dictionaryList.layoutManager = LinearLayoutManager(requireContext())
        binding.dictionaryList.itemAnimator = null
    }

    private val searchQueryObserver = Observer<Cursor> {
        adapter.cursor = it
        binding.dictionaryList.adapter = adapter
        binding.dictionaryList.itemAnimator = null
    }

    private val favouriteObserver = Observer<Cursor> {
        adapter.cursor = it
        adapter.notifyDataSetChanged()
        binding.dictionaryList.itemAnimator = null
//        binding.dictionaryList.adapter = adapter
    }
    private val noResultObserver = Observer<Unit> {
        binding.placeHolder.visibility = View.VISIBLE
    }
    private val haveResultObserver = Observer<Unit> {
        binding.placeHolder.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}