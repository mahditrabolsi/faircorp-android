package com.mahdi.faircorp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.mahdi.faircorp.FaircorpApplication
import com.mahdi.faircorp.dao.WindowDao
import com.mahdi.faircorp.dto.WindowDto
import com.mahdi.faircorp.model.Window
import com.mahdi.faircorp.retrofit.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WindowViewModel(private val windowDao: WindowDao) : BaseViewModel() {

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val windowDao = (extras[APPLICATION_KEY] as FaircorpApplication).database.windowDao()
                return WindowViewModel(windowDao) as T
            }
        }
    }

    fun findByRoomId(roomId: Long): LiveData<List<WindowDto>> = liveData {
        val elements: List<WindowDto> = withContext(Dispatchers.IO) {
            try {
                val response = ApiServices.windowApiService.findWindowsByRoomId(roomId).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val windows: List<WindowDto> = response.body() ?: emptyList()
                windows.apply {
                    Log.e("windows", windows.toString())
                    windowDao.clearByRoomId(roomId)
                    forEach {
                        windowDao.create(
                            Window(
                                id = it.id.toInt(),
                                name = it.name,
                                roomId = it.roomId,
                                roomName = it.roomName,
                                windowStatus = it.windowStatus
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                windowDao.findByRoomId(roomId).map { it.toDto() }
            }
        }
        emit(elements)
    }

    fun createWindow(window: WindowDto) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            try {
                ApiServices.windowApiService.createWindow(window).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
            }
        }
    }
    fun deleteWindow(window: WindowDto) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            try {
                ApiServices.windowApiService.deleteById(window.id).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
            }
        }
    }


}