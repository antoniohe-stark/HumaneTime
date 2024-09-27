package com.example.humanetime.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Esta funcionalidad sigue en contruccion\n Por favor no descarte esta postualción :)"
    }
    val text: LiveData<String> = _text
}