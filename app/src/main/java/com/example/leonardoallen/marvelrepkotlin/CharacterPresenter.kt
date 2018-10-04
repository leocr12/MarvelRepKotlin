package com.example.leonardoallen.marvelrepkotlin

class CharacterPresenter(private val view: CharacterContract.View): CharacterContract.Presenter {

    override fun init() {
        show()
    }

    private fun show() {
        retrieveDatabase()
        retrieveCharacters()
    }

    private fun success(response: CharactersResponse) {
        view.hideProgressBar()
        response.data?.results?.let {
            view.loadFirstPage(it,
                response.data.count,
                response.data.total)
        }
        insertCharactersIntoDatabase(response.data?.results!!)
    }

    private fun successMore(response: CharactersResponse) {
        view.hideProgressBar()
        response.data?.results?.let {
            view.loadNextPage(it,
                response.data.count)
        }
        response.data?.results?.let { insertCharactersIntoDatabase(it) }
    }

    private fun error(t: Throwable) {
        t.printStackTrace()
        if (!Injector.provideNetworkUtil().isConnected) {
            view.showNetworkError()
            retrieveAllCharactersFromDB()
        }
        view.hideProgressBar()
    }

    private fun errorMore(t: Throwable) {
        view.hideProgressBar()
        t.printStackTrace()
    }

    override fun loadMoreCharacters(offset: Int) {
        Injector.provideCharacterRepository().fetchMoreCharacters(offset)
                .subscribe(this::successMore, this::errorMore)
    }

    override fun retrieveCharacters() {
        Injector.provideCharacterRepository().fetchCharacters()
                .subscribe(this::success, this::error)
    }

    override fun retrieveAllCharactersFromDB() {
        val characters = retrieveAllCharacters()
        view.setCharacterData(characters)
    }

    override fun insertCharactersIntoDatabase(characters: MutableList<Character>) {
        Injector.provideDatabaseHelper().insertCharacters(characters)
    }

    override fun retrieveAllCharacters(): MutableList<Character> {
        return Injector.provideDatabaseHelper().allCharacters
    }

    override fun retrieveDatabase() {
        Injector.provideDatabaseHelper().marvelDatabase
    }
}