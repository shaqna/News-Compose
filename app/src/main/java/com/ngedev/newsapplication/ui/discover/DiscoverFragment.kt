package com.ngedev.newsapplication.ui.discover

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.BuildCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ngedev.newsapplication.databinding.FragmentDiscoverBinding
import com.ngedev.newsapplication.ui.detail.DetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


@BuildCompat.PrereleaseSdkCheck class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiscoverViewModel by viewModel()

    private val pagingAdapter: ArticleAdapter by lazy {
        ArticleAdapter().apply {
            onItemClickListener = { article ->
                Intent(requireActivity(), DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.TAG, article)
                    startActivity(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(DiscoverViewModel.inject())
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
        lifecycleScope.launch {
            binding.rvArticle.apply {
                adapter = pagingAdapter
                layoutManager = LinearLayoutManager(requireActivity())
            }
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listArticle.collectLatest { listItem ->
                    pagingAdapter.apply {
                        addLoadStateListener { loadState ->
                            loadState.decideOnState(
                                showLoading = { visible ->
                                    showProgressBar(visible)
                                },
                                showEmpty = {

                                },
                                showError = { message ->
                                    Log.d("My Error", message)
                                    Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
                                        .show()
                                }
                            )
                        }
                        submitData(listItem)
                        loadStateFlow.distinctUntilChanged()
                        loadStateFlow.collectLatest {
                            if (it.refresh is LoadState.NotLoading) {
                                showProgressBar(false)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initFirstList() {
        viewModel.getArticleRelateWith("google")
    }

    private inline fun CombinedLoadStates.decideOnState(
        showLoading: (Boolean) -> Unit,
        showEmpty: (Boolean) -> Unit,
        showError: (String) -> Unit
    ) {
        showLoading(refresh is LoadState.Loading)

        showEmpty(
            source.append is LoadState.NotLoading
                    && source.append.endOfPaginationReached
                    && pagingAdapter.itemCount == 0
        )

        val errorState = source.append as? LoadState.Error
            ?: source.prepend as? LoadState.Error
            ?: source.refresh as? LoadState.Error
            ?: append as? LoadState.Error
            ?: prepend as? LoadState.Error
            ?: refresh as? LoadState.Error

        errorState?.let {
            showError(it.error.toString())
        }
    }

    private fun showProgressBar(visible: Boolean) {
        binding.rvArticle.isVisible = !visible
        binding.progressBar.isVisible = visible
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