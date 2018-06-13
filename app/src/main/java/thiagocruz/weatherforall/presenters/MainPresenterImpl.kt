package thiagocruz.weatherforall.presenters

import thiagocruz.weatherforall.interactors.MainInteractor
import thiagocruz.weatherforall.interactors.MainInteractorImpl
import thiagocruz.weatherforall.views.MainView

class MainPresenterImpl : MainPresenter {

    var mainView: MainView? = null
    var mainInteractor: MainInteractor? = null

    override fun attachView(mainView: MainView) {
        this.mainView = mainView
        mainInteractor = MainInteractorImpl()
    }
}
