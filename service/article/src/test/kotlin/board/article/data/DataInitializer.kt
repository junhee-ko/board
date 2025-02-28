package board.article.data

import board.article.entity.Article
import board.common.snowflake.Snowflake
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.support.TransactionTemplate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
class DataInitializer {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var transactionTemplate: TransactionTemplate

    private val snowflake = Snowflake()
    private val latch = CountDownLatch(EXECUTE_COUNT)

    @Test
    fun initialize() {
        val executeService: ExecutorService = Executors.newFixedThreadPool(10)
        for (i in 1..EXECUTE_COUNT) {
            executeService.submit {
                insert()
                latch.countDown()
                println("latch.countDown: ${latch.count}")
            }
        }
        latch.await()
        executeService.shutdown()
    }

    private fun insert() {
        transactionTemplate.executeWithoutResult {
            for (i in 0..BULK_INSERT_SIZE) {
                val article = Article.create(
                    articleId = snowflake.nextId(),
                    title = "title $i",
                    content = "content $i",
                    boardId = 1L,
                    writerId = 1L
                )
                entityManager.persist(article)
            }
        }
    }

    companion object {
        private const val EXECUTE_COUNT: Int = 6000
        private const val BULK_INSERT_SIZE: Int = 2000
    }
}