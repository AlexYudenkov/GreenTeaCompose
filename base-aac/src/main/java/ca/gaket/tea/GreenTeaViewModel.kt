package ca.gaket.tea

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.gaket.tea.runtime.coroutines.GreenTeaRuntime
import ca.gaket.tea.runtime.coroutines.Update
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class GreenTeaViewModel<State : Any, Message : Any, Dependencies : Any>(
  init: Update<State, Message, Dependencies>,
  update: (Message, State) -> Update<State, Message, Dependencies>,
  dependencies: Dependencies
) : ViewModel() {

  private val TAG = this::class.simpleName

  private val runtime by lazy {
    GreenTeaRuntime(
      init = { init },
      update = update,
      dependencies = dependencies,
      exceptionHandler = CoroutineExceptionHandler { _, throwable -> Log.e(TAG, "Unhandled exception", throwable) }
    )
  }

  private val _state: MutableStateFlow<State?> = MutableStateFlow(null)


  val state: StateFlow<State?> = _state.asStateFlow()
//  val state: Flow<State> = _state.filterNotNull()

  //instead onCreated
  init{
    runtime.listenState {
      viewModelScope.launch {
        _state.emit(it)
      }
    }
  }

//  @CallSuper
//  open fun onCreated() {
//    runtime.listenState {
//      viewModelScope.launch {
//        _state.emit(it)
//      }
//    }
//  }

  override fun onCleared() {
    runtime.cancel()
  }

  fun dispatch(msg: Message) = runtime.dispatch(msg)
}
