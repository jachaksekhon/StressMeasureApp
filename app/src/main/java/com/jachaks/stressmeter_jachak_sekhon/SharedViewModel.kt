package com.jachaks.stressmeter_jachak_sekhon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// viewmodel to share btwn mainactivity and stress

class SharedViewModel : ViewModel() {
    val isSoundPlaying = MutableLiveData<Boolean>(false)
}
