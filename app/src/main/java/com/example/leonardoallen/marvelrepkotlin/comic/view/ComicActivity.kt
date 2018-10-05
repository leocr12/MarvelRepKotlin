package com.example.leonardoallen.marvelrepkotlin.comic.view

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.leonardoallen.marvelrepkotlin.BaseActivity
import com.example.leonardoallen.marvelrepkotlin.util.PaginationScrollListener
import com.example.leonardoallen.marvelrepkotlin.R
import com.example.leonardoallen.marvelrepkotlin.character.model.Character
import com.example.leonardoallen.marvelrepkotlin.character.view.CharacterAdapter.Companion.EXTRA_MESSAGE
import com.example.leonardoallen.marvelrepkotlin.comic.contract.ComicContract
import com.example.leonardoallen.marvelrepkotlin.comic.presenter.ComicPresenter
import com.example.leonardoallen.marvelrepkotlin.comic.model.Comic

class ComicActivity : BaseActivity(), ComicContract.View {

    private var parent: ViewGroup? = null
    private var comicAdapter: ComicAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    private var isLoading = false
    private var isLastPage = false
    private var totalComics: Int = 0
    private var offset: Int = 0
    private var count: Int = 0

    private var progressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null

    private val presenter: ComicPresenter by lazy {
        ComicPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)
        initView(viewGroup)
        val character = intent.getParcelableExtra<Character>(EXTRA_MESSAGE)
        presenter.init(character.id)
        title = character.name
    }

    private fun initView(parent: ViewGroup) {
        progressBar = findViewById(R.id.comic_progress_bar)
        recyclerView = findViewById(R.id.comic_recycler_view)
        this.parent = parent
        initComicAdapter()
    }

    override fun setComicData(comics: MutableList<Comic>) {
        comicAdapter!!.setComicData(comics)
    }

    private fun initComicAdapter() {
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(parent!!.context)
        recyclerView!!.layoutManager = layoutManager
        comicAdapter = ComicAdapter()
        recyclerView!!.adapter = comicAdapter
        addOnScrollListenerRecyclerView()
    }

    override fun hideProgressBar() {
        progressBar!!.visibility = View.INVISIBLE
    }

    override fun addOnScrollListenerRecyclerView() {
        recyclerView!!.addOnScrollListener(object : PaginationScrollListener(layoutManager!!) {
            override fun loadMoreItems() {
                this@ComicActivity.isLoading = true
                this@ComicActivity.offset += this@ComicActivity.count
                Handler().postDelayed({ presenter.loadMoreComics(this@ComicActivity.offset) }, 100)
            }

            override fun getTotalPageCountR(): Int {
                return this@ComicActivity.totalComics
            }

            override fun isLastPageR(): Boolean {
                return this@ComicActivity.isLastPage
            }

            override fun isLoadingR(): Boolean {
                return this@ComicActivity.isLoading
            }
        })
    }

    override fun loadFirstPage(comics: MutableList<Comic>, total: Int, count: Int) {
        setComicData(comics)
        totalComics = total
        this.count = count
        if (offset <= total) {
            comicAdapter!!.addLoadingFooter()
        } else {
            isLastPage = true
        }
    }

    override fun loadNextPage(comics: MutableList<Comic>, count: Int) {
        this.count = count
        comicAdapter!!.removeLoadingFooter()
        isLoading = false
        comicAdapter!!.addAll(comics)
        if (offset != totalComics) {
            comicAdapter!!.addLoadingFooter()
        } else {
            isLastPage = true
        }
    }

    override fun setCharacterId(comics: MutableList<Comic>, characterId: Int) {
        for (c in comics) {
            c.characterId = characterId
        }
    }

    override fun showEmptyComicError() {
        Toast.makeText(this@ComicActivity, "This character has no Comic saved in Database",
                Toast.LENGTH_SHORT).show()
        finish()
    }
}
