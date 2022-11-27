package com.alex.greenteacomposeexample

import ca.gaket.tea.runtime.coroutines.Update
import ca.gaket.tea.runtime.coroutines.noEffects
import ca.gaket.tea.runtime.coroutines.with

object GreenTeaFeature {

    data class State(
        val isLoading: Boolean,
        val message: String,
    )

    object Logic {
        val initialUpdate = State(
            isLoading = false,
            message = ""
        ) with noEffects<Message, Dependencies>()

        fun update(message: Message, state: State): Update<State, Message, Dependencies> =
            when (message) {
                is Message.TextUpdated -> {
                    handleTextUpdate(
                        message,
                        state
                    )
                }
            }

        private fun handleTextUpdate(
            message: Message.TextUpdated,
            state: State
        ): Update<State, Message, Dependencies> {
            return state.copy(
                isLoading = false,
                message = message.query
            ) with noEffects()
        }
    }

    sealed class Message {
        data class TextUpdated(val query: String) : Message()
    }
    class Dependencies(
    )
}