package io.kaendagger.apodcompanion.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.kaendagger.ui.home.MainActivityViewModel
import io.kaendagger.apodcompanion.viewModelCommons.ViewModelFactory
import io.kaendagger.apodcompanion.data.APODRepository
import io.kaendagger.apodcompanion.viewModelCommons.ViewModelKey
import javax.inject.Provider

@Module(includes = [APODRoomModule::class])
class ViewModelModule {

    @Provides
    fun viewModelFactory(provideMap: MutableMap<Class<out ViewModel>,Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory(provideMap)
    }

    @Provides
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun maViewModel(
        context:Context,
        picasso: Picasso,
        apodRepository: APODRepository
    ): MainActivityViewModel {
        return MainActivityViewModel(
            context,
            picasso,
            apodRepository
        )
    }

}