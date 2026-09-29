package com.example.retrofit_radier

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit_radier.network.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class PostDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    // O Navigation coloca os argumentos da rota ("detail/{postId}") no SavedStateHandle
    private val postId: Int = checkNotNull(savedStateHandle["postId"])

    private val _uiState = MutableStateFlow<PostDetailUiState>(PostDetailUiState.Loading)
    val uiState: StateFlow<PostDetailUiState> = _uiState.asStateFlow()

    init {
        fetchPost()
    }

    fun fetchPost() {
        _uiState.value = PostDetailUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                // As duas requisições rodam em paralelo; await() espera cada uma terminar
                val post = async { RetrofitClient.apiService.getPost(postId) }
                val comments = async { RetrofitClient.apiService.getComments(postId) }
                PostDetailUiState.Success(post.await(), comments.await())
            } catch (e: IOException) {
                PostDetailUiState.Error("Sem conexão com a internet. Verifique sua rede.")
            } catch (e: Exception) {
                PostDetailUiState.Error("Erro ao carregar o post: ${e.message}")
            }
        }
    }
}
