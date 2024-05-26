# Kontext

kontext is a simple library for context management in Kotlin without scope restriction.
I would be happy if this feature is added to the Kotlin language in the future.

## Why Kontext?

Sometimes you need to pass a context object to a function or class, but you can't access the context object directly.
Recently, Kotlin has introduced a new feature called "Context Receiver" that allows you to access the context object
 directly, but the context object is limited to the scope of the function or class.

kontext allows you to create a context object and access it from anywhere in the code.

### Use Cases
**Logging** - You can create a context object to store some information when you emit log.

**Settings** - You can create a context object to store settings information.

**Authentication** - You can create a context object to store user information and access anywhere.

## Getting Started

### 1. Installing

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies 
    implementation("com.github.AntoD3v:kontext:1.0.0")
}
```

### 2. Usage

```kotlin

// 1. Create a context object
data class ExampleContext(
    var field1: Boolean = false,
    var field2: Int
) {

    companion object : Context<ExampleContext>()

}

// 2. Create a global context (optional)
createGlobalContext(ExampleContext)

// 3. Create a context
createContext(ExampleContext) {

    // 4. Use context when you can't access the context object directly
    val context = useContext(ExampleContext)

}

```

## Code Example

```kotlin
data class SettingsContext(
    var lightMode: Boolean = false
) {

    companion object : Context<SettingsContext>() {

        /**
         * [createContextObject] is called when a new context is created.
         * @param parent the parent context
         *
         * By default, if the method is not overridden, the parent context is passed to the child context.
         */
        override fun createContextObject(parent: SettingsContext?): SettingsContext = SettingsContext(
            lightMode = !(parent?.lightMode ?: false)
        )

    }

}

fun main() {

    createGlobalContext(SettingsContext) // create global context (SettingsContext #0)

    createContext(SettingsContext) { // SettingsContext #1 (lightMode = false)

        createContext(SettingsContext) {// SettingsContext #2 (lightMode = true)

            val settings = useContext(SettingsContext) // settings.lightMode = true (SettingsContext #2)

            useContext(SettingsContext) {

                createContext(SettingsContext) { // SettingsContext #3 (lightMode = false)

                    OtherTask.doSomething1()

                }

                OtherTask.doSomething2()

            }

        }

    }

}


object OtherTask {

    fun doSomething1() {

        val settings = useContext(SettingsContext) // settings.lightMode = false (SettingsContext #3)

    }

    fun doSomething2() {

        useContext(SettingsContext) {

            // settings.lightMode = true (SettingsContext #2)

        }

    }
}
```
