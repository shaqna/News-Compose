package com.ngedev.newsapplicationcompose.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.BuildCompat
import androidx.fragment.app.Fragment
import androidx.paging.compose.collectAsLazyPagingItems
import com.ngedev.newsapplicationcompose.databinding.FragmentDiscoverBinding
import com.ngedev.newsapplicationcompose.ui.discover.components.ListArticle
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


@BuildCompat.PrereleaseSdkCheck
class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiscoverViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(DiscoverViewModel.inject())
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFirstList()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.isNotEmpty()) {
                    viewModel.getArticleRelateWith(query)
                } else {
                    initFirstList()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        binding.composeListView.setContent {
            MaterialTheme {
                val context = LocalContext.current
                val articles = viewModel.listArticle.collectAsLazyPagingItems()
                ListArticle(context = context, items = articles)
            }
        }


    }

    private fun initFirstList() {
        viewModel.getArticleRelateWith("google")
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}