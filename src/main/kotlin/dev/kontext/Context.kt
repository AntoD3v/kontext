package dev.kontext

import java.util.*
import java.util.Collections.emptyMap
import kotlin.reflect.KClass

abstract class Context<Object : Any> {

    protected val stack = Stack<Object>()
    protected lateinit var clazzObject: KClass<Object>

    fun useContext(): Object = stack.lastOrNull() ?: throw IllegalStateException("No context found")
    fun useContextOrNull(): Object? = stack.lastOrNull()

    fun useGlobalContext(): Object = stack.firstOrNull() ?: throw IllegalStateException("No context found")
    fun useGlobalContextOrNull(): Object? = stack.firstOrNull()

    fun <ReturnType> context(block: Object.() -> ReturnType): ReturnType = useContext().block()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified O : Object, ReturnType> createContext(noinline block: O.() -> ReturnType): ReturnType =
        createContext(O::class as KClass<Object>, block as (Object.() -> ReturnType))

    fun <ReturnType> createContext(clazzObject: KClass<Object>, block: Object.() -> ReturnType): ReturnType {

        if (!this::clazzObject.isInitialized) this.clazzObject = clazzObject
        val contextObject = createContextObject()
        stack.add(contextObject)
        val returnType = contextObject.block()
        stack.remove(contextObject)
        return returnType

    }

    fun createGlobalContext(clazzObject: KClass<Object>) {

        if (!this::clazzObject.isInitialized) this.clazzObject = clazzObject
        val contextObject = createContextObject()
        createGlobalContext(contextObject)

    }

    @Suppress("UNCHECKED_CAST")
    fun createGlobalContext(contextObject: Object) {
        if (!this::clazzObject.isInitialized) this.clazzObject = contextObject::class as KClass<Object>
        stack.add(0, contextObject)
    }

    protected open fun createContextObject(parent: Object? = stack.lastOrNull()): Object =
        clazzObject.constructors.firstOrNull { it.parameters.isEmpty() }?.callBy(emptyMap())
            ?: throw IllegalStateException("No empty constructor found for class $clazzObject")

}

inline fun <reified Object : Any, ReturnType> createContext(
    context: Context<Object>, noinline block: Object.() -> ReturnType
): ReturnType = context.createContext(Object::class, block)

inline fun <reified Object : Any> createGlobalContext(context: Context<Object>): Object {
    context.createGlobalContext(Object::class)
    return context.useContext()
}

fun <Object : Any, ReturnType> useContext(context: Context<Object>, block: Object.() -> ReturnType): ReturnType =
    context.useContext().block()

fun <Object : Any> useContext(context: Context<Object>): Object = context.useContext()

