package thiagocruz.weatherforall.presenters

import thiagocruz.weatherforall.views.MainView

interface MainPresenter {
    fun attachView(mainView: MainView)
}