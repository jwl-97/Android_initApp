package com.ljw.initapp.util

interface StackImplement<Type> {
    fun size(): Int
    fun push(item: Type)
    fun pop(): Type
    fun peek(): Type
    fun isEmpty(): Boolean
    fun clear()
}

class Stack<E> : StackImplement<E> {
    val list = mutableListOf<E>()

    override fun size(): Int {
        return list.size
    }

    override fun push(item: E) {
        list.add(item)
    }

    override fun pop(): E {
        return list.removeAt(list.size - 1)
    }

    override fun peek(): E {
        return list[list.size - 1]
    }

    override fun isEmpty(): Boolean {
        return list.size == 0
    }

    override fun clear() {
        return list.clear()
    }
}
