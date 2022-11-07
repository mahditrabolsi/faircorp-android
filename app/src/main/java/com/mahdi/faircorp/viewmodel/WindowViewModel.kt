package com.mahdi.faircorp.viewmodel

import android.util.Log
import android.view.WindowId
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.mahdi.faircorp.FaircorpApplication
import com.mahdi.faircorp.dao.WindowDao
import com.mahdi.faircorp.dto.WindowDto
import com.mahdi.faircorp.dto.WindowStatus
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
                                id = it.id,
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

    fun createWindow(window: WindowDto) = liveData {
        val element: WindowDto = withContext(Dispatchers.IO) {
            try {
                val response = ApiServices.windowApiService.createWindow(window).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val window: WindowDto = response.body() ?: window
                window.apply {
                    windowDao.create(
                        Window(
                            id = id,
                            name = name,
                            roomId = roomId,
                            roomName = roomName,
                            windowStatus = windowStatus
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                windowDao.findById(window.id!!)!!.toDto()
            }
        }
        emit(element)
    }
    fun deleteWindow(windowId: Long) = liveData {
        val element: WindowDto = withContext(Dispatchers.IO) {
            try {
                val response = ApiServices.windowApiService.deleteById(windowId).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val window: WindowDto = (response.body() ?: throw Exception("Window not found")) as WindowDto
                window.apply {
                    windowDao.deleteById(windowId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                WindowDto(id = windowId, name = "Unknown", roomId = 0, roomName = "Unknown", windowStatus = WindowStatus.CLOSED)
            }
        }
        emit(element)
    }


}