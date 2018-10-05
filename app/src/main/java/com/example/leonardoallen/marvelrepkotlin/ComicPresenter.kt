package com.example.leonardoallen.marvelrepkotlin

class ComicPresenter(private val view: ComicContract.View): ComicContract.Presenter {

    private var characterId: Int = 0

    override fun init(characterId: Int) {
        show(characterId)
    }

    private fun show(characterId: Int) {
        retrieveDatabase()
        retrieveComics(characterId)
    }

    private fun success(response: ComicResponse) {
        view.hideProgressBar()
        response.data?.results?.let {
            view.loadFirstPage(it,
                response.data.total, response.data.count)
        }

        insertComicsIntoDatabase(response)
    }

    private fun error(t: Throwable) {
        t.printStackTrace()
        if (!Injector.provideNetworkUtil().isConnected) {
            retrieveComicsFromDatabase(characterId)
        }

        if (isComicEmpty(characterId)) {
            view.showEmptyComicError()
        }

        view.hideProgressBar()
    }

    private fun successMore(response: ComicResponse) {
        view.hideProgressBar()
        response.data?.results?.let {
            view.loadNextPage(it,
                response.data.count)
        }
        insertComicsIntoDatabase(response)
    }

    private fun errorMore(t: Throwable) {
        t.printStackTrace()
    }

    private fun isComicEmpty(characterId: Int): Boolean {
        return Injector.provideDatabaseHelper().isComicEmpty(characterId)
    }

    override fun retrieveDatabase() {
        Injector.provideDatabaseHelper().marvelDatabase
    }

    override fun retrieveComics(characterId: Int) {
        this.characterId = characterId
        Injector.provideComicRepository().fetchComics(characterId)
                .subscribe(this::success, this::error)
    }

    override fun loadMoreComics(offset: Int) {
        Injector.provideComicRepository().fetchMoreComics(characterId, offset)
                .subscribe(this::successMore, this::errorMore)
    }

    override fun retrieveComicsFromDatabase(characterId: Int) {
        val comics = retrieveAllComics(characterId)
        view.setComicData(comics)
    }

    override fun retrieveAllComics(characterId: Int): MutableList<Comic> {
        return Injector.provideDatabaseHelper().getComics(characterId)
    }

    override fun insertComicsIntoDatabase(comicResponse: ComicResponse) {
        comicResponse.data?.results?.let { view.setCharacterId(it, characterId) }
        comicResponse.data?.results?.let { insertComicsIntoDatabase(it) }
    }

    override fun insertComicsIntoDatabase(comics: MutableList<Comic>) {
        Injector.provideDatabaseHelper().insertComics(comics)
    }

}
