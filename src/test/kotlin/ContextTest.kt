import dev.kontext.Context
import dev.kontext.createContext
import dev.kontext.createGlobalContext
import dev.kontext.useContext
import kotlin.test.Test
import kotlin.test.assertEquals

data class TestContext(
    var index: Int = 0
) {

    companion object : Context<TestContext>() {

        override fun createContextObject(parent: TestContext?): TestContext = TestContext(
            index = (parent?.index ?: 0) + 1
        )

    }

}

class ContextTest {

    @Test
    fun simpleContextTest() {

        createContext(TestContext) {

            createContext(TestContext) {

                val context = useContext(TestContext)

                assertEquals(1, this.index)
                assertEquals(1, context.index)

            }

            val context = useContext(TestContext)

            assertEquals(0, this.index)
            assertEquals(0, context.index)

        }

    }

    @Test
    fun globalContextTest() {

        createGlobalContext(TestContext)

        val context = useContext(TestContext)

        assertEquals(0, context.index)

    }

}