package com.kennycason.catuskoti.catuskoti

import com.kennycason.catuskoti.catuskoti.math.QuadBitFunction


class Node(private val function: QuadBitFunction,
           private val parents: MutableList<Node>,
           private val children: MutableList<Node>,
           private var visited: Boolean = false,
           private var value: QuadBit?) {

    fun connect(child: Node) {
        children.add(child)
        child.parents.add(this)
    }

    fun evaluate() {
        if (visited) { return }
        visited = true

        parents.forEach(Node::evaluate)

        // now each parent has ben evaluated, apply parent values to current node
        parents.forEach { parent ->
            if (parent.value == null) { return@forEach }

            if (value != null) {
                value = parent.value
            } else {
                value = function.apply(value!!, parent.value!!)
            }
        }

        // evaluate children nodes
        if (value != null) {
            children.forEach(Node::evaluate)
        }
    }

    fun value() = value

    fun clear() {
        if (!visited) { return }
        visited = false
        value = null

        children.forEach(Node::clear)
    }

}