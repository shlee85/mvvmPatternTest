package com.example.mvvmpatterntest

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmpatterntest.databinding.ActivityMainBinding

//View
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()
        setupClickListener()
    }

    private fun setupObserver() {
        mainViewModel.data.observe(this) {
            binding.textview.text = it
        }
    }

    private fun setupClickListener() {
        binding.button.setOnClickListener {
            mainViewModel.getData()
        }
    }
}

//View모델
class MainViewModel(): ViewModel() {
    private val mainRepository = MainRepository()

    //데이터를 캡슐화하여 외부(뷰)에서 접근할 수 없도록한다.
    //외부 접근 프로퍼티는 immutable 타입으로 제한해 변경할 수 없도록 한다.
    private val _data = MutableLiveData<String>("")
    val data: LiveData<String> = _data

    fun getData() {
        _data.value = mainRepository.getData()
    }
}

//Model
class MainRepository() {

    //일반적으로 local db or api데이터를 호출 한다
    //이번 예제에서는 간단히 repository의 프로퍼티 값을 호출하는 방식으로 작성해보았다.
    private val data: String = "DATA 호출 성공 in Main Repository"

    fun getData(): String {
        return data
    }
}