package com.example.retrofit_radier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit_radier.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class PostViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PostUiState>(PostUiState.Loading)
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        _uiState.value = PostUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                PostUiState.Success(RetrofitClient.apiService.getPosts())
            } catch (e: IOException) {
                // Falha de rede: sem internet, DNS, timeout...
                PostUiState.Error("Sem conexão com a internet. Verifique sua rede.")
            } catch (e: Exception) {
                // Qualquer outro problema (erro HTTP, JSON inválido...)
                PostUiState.Error("Erro ao carregar os posts: ${e.message}")
            }
        }
    }
}
