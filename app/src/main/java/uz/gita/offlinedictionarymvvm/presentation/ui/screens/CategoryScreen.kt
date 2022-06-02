package uz.gita.offlinedictionarymvvm.presentation.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.offlinedictionarymvvm.R
import uz.gita.offlinedictionarymvvm.databinding.ScreenCategoryBinding
import uz.gita.offlinedictionarymvvm.presentation.ui.adapter.CategoryAdapter
import uz.gita.offlinedictionarymvvm.presentation.ui.dialog.AboutDialog
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.CategoryViewModel
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.impl.CategoryViewModelImpl

class CategoryScreen : Fragment(R.layout.screen_category) {
    private val binding by viewBinding(ScreenCategoryBinding::bind)
    private val viewModel: CategoryViewModel by viewModels<CategoryViewModelImpl>()
    private val categoryAdapter = CategoryAdapter()
    private val dialog = AboutDialog()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.allWordsCategoryLiveData.observe(viewLifecycleOwner, allWordsCategoryObserver)
        viewModel.openByCategoryLiveData.observe(this@CategoryScreen, openByCategoryObserver)
        viewModel.openDrawerLiveData.observe(viewLifecycleOwner, openDrawerObserver)
        viewModel.closeDrawerLiveData.observe(viewLifecycleOwner, closeDrawerObserver)
        viewModel.openPlayMarketLiveData.observe(this@CategoryScreen, openPlayMarketObserver)
        viewModel.openAboutDialogLiveData.observe(this@CategoryScreen, openAboutDialogLiveData)

        categoryAdapter.setOnClickItemListener { title, id ->
            viewModel.openByCategory(id, title)
            viewModel.closeDrawerLayout()
        }

        binding.burgerButton.setOnClickListener {
            viewModel.openDrawerLayout()
        }
        binding.navigationLayout.drawerCategory.setOnClickListener {

            viewModel.closeDrawerLayout()
        }
        binding.navigationLayout.drawerFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_categoryScreen_to_favouriteScreen)
        }
        binding.navigationLayout.draverRate.setOnClickListener {
            viewModel.onClickRate()
        }
        binding.navigationLayout.drawerAbout.setOnClickListener {
            viewModel.onClickAbout()
        }
    }

    private val allWordsCategoryObserver = Observer<Cursor> {
        categoryAdapter.cursor = it
        binding.recyclerViewListCategory.adapter = categoryAdapter
        binding.recyclerViewListCategory.layoutManager = LinearLayoutManager(requireContext())
    }

    private val openByCategoryObserver = Observer<Pair<Int, String>> {
        findNavController().navigate(
            CategoryScreenDirections.actionCategoryScreenToDictionaryScreen(
                it.first, it.second
            )
        )
    }
    private val openDrawerObserver = Observer<Unit> {
        binding.drawer.openDrawer(GravityCompat.START)
    }
    private val closeDrawerObserver = Observer<Unit> {
        binding.drawer.closeDrawer(GravityCompat.START)
    }
    private val openPlayMarketObserver = Observer<Unit> {
//        viewModel.openPlayMarketLiveData.observe(this@CategoryScreen) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${requireActivity().packageName}")
            )
        )
//        }
    }

    private val openAboutDialogLiveData = Observer<Unit> {

        dialog.show(childFragmentManager, "Dialog")
        dialog.isCancelable = false
    }
}