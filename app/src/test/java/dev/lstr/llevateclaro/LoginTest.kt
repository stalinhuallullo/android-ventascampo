package dev.lstr.llevateclaro

import android.app.Application
//import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import dev.lstr.llevateclaro.presentation.presenter.view_model.LoginViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

//import org.mockito.Mockito


/**
 * Created by lesternr on 6/24/18.
 */
class LoginTest {

//    @Mock lateinit var observerMessage: Observer<String>
//    @Mock lateinit var observerResult: Observer<LiveResult>

//    @Rule @JvmField val rule = InstantTaskExecutorRule()

    val vmTested = LoginViewModel()

    @Before
    fun setUp() {
//        MockitoAnnotations.initMocks(this)
    }

    @Test fun validateUser_Success() {
//        vmTested.initViewModel(getTestContext())
//        vmTested.messageLiveData.observeForever(observerMessage)
//        vmTested.resultLiveData.observeForever(observerResult)
//
//        vmTested.login("pepe2@yopmail.com", "12345")
//
//        println(vmTested.messageLiveData.value)
//
//        assert(vmTested.messageLiveData.value != "")
    }

    @Test fun validateUser_Success2() {
//        vmTested.initViewModel(getTestContext())
//        vmTested.messageLiveData.observeForever(observerMessage)
//        vmTested.resultLiveData.observeForever(observerResult)
//
//        vmTested.login("jauregui.crespo@gmail.com", "1")
//
//        println(vmTested.messageLiveData.value)
//
//        assert(vmTested.messageLiveData.value != "")
    }

    @Test fun validateUser_Failed() {

        val messageObserver = mock<Observer<String>>()

//        val observer = Mockito.mock(<Observer<String>())

        vmTested.initViewModel(getTestContext())
        vmTested.messageLiveData.observeForever(messageObserver)
//        vmTested.messageLiveData.observeForever(observerMessage)
//        vmTested.resultLiveData.observeForever(observerResult)

        vmTested.login("pepe@yopmail.com", "12345")

        println(vmTested.messageLiveData.value)


//        verify(messageObserver).onChanged("Usuario no registrado")

//        Assert.assertEquals(messageObserver, "Usuario no registrado")
//        assert(vmTested.messageLiveData.value == "Usuario no registrado")

//        Mockito.verify(messageObserver).onChanged("Usuario no registrado")
    }


    fun getTestContext(): Context {
        val application:Application = Mockito.mock(Application::class.java)
        Mockito.`when`(application.applicationContext).thenReturn(application)
        return application
    }
}