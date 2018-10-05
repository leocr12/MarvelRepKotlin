package com.example.leonardoallen.marvelrepkotlin.character.view

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.leonardoallen.marvelrepkotlin.BaseActivity
import com.example.leonardoallen.marvelrepkotlin.util.PaginationScrollListener
import com.example.leonardoallen.marvelrepkotlin.R
import com.example.leonardoallen.marvelrepkotlin.character.contract.CharacterContract
import com.example.leonardoallen.marvelrepkotlin.character.model.Character
import com.example.leonardoallen.marvelrepkotlin.character.presenter.CharacterPresenter
import kotlinx.android.synthetic.main.activity_character.*

class CharacterActivity : BaseActivity(), CharacterContract.View {

    private var parent: ViewGroup? = null
    private var characterAdapter: CharacterAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    private var isLoading = false
    private var isLastPage = false
    private var offset: Int = 0
    private var totalCharacters: Int = 0
    private var count: Int = 0

    private val presenter: CharacterPresenter by lazy {
        CharacterPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        initView(viewGroup)
        title = getString(R.string.characters_title)
        presenter.init()
    }

    private fun initView(parent: ViewGroup) {
        this.parent = parent
        initCharacterAdapter()
    }

    override fun setCharacterData(characters: MutableList<Character>) {
        characterAdapter!!.setListData(characters)
    }

    override fun hideProgressBar() {
        characterProgressBar!!.visibility = View.INVISIBLE
    }

    private fun initCharacterAdapter() {
        characterAdapter = CharacterAdapter()
        characterRecyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(parent!!.context)
        characterRecyclerView!!.layoutManager = layoutManager
        characterRecyclerView!!.adapter = characterAdapter
        addOnScrollListenerRecyclerView()
    }

    override fun addOnScrollListenerRecyclerView() {
        layoutManager?.let {
            characterRecyclerView!!.addOnScrollListener(object : PaginationScrollListener(it) {
            override fun loadMoreItems() {
                this@CharacterActivity.isLoading = true
                this@CharacterActivity.offset += this@CharacterActivity.count

                Handler().postDelayed({
                    presenter.loadMoreCharacters(offset)
                }, 100)
            }

            override fun getTotalPageCountR(): Int {
                return this@CharacterActivity.totalCharacters
            }

            override fun isLastPageR(): Boolean {
                return this@CharacterActivity.isLastPage
            }

            override fun isLoadingR(): Boolean {
                return this@CharacterActivity.isLoading
            }
        })
        }
    }

    override fun loadFirstPage(characters: MutableList<Character>, count: Int, total: Int) {
        setCharacterData(characters)
        totalCharacters = total
        this.count = count
        if (offset <= total) {
            characterAdapter!!.addLoadingFooter()
        } else {
            isLastPage = true
        }
    }

    override fun loadNextPage(characters: MutableList<Character>, count: Int) {
        this.count = count
        characterAdapter!!.removeLoadingFooter()
        isLoading = false
        characterAdapter!!.addAll(characters)

        if (offset != totalCharacters) {
            characterAdapter!!.addLoadingFooter()
        } else {
            isLastPage = true
        }
    }

    override fun showNetworkError() {
        Toast.makeText(this@CharacterActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
    }
}
