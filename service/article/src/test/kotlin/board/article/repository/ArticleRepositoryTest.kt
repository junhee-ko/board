package board.article.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun findAllTest() {
        val articles = articleRepository.findAll(
            boardId = 1L,
            offset = 149970L,
            limit = 30L
        )

        println(articles.size)
        articles.forEach {
            println("article: $it")
        }
    }

    @Test
    fun countTest() {
        val count = articleRepository.count(1L, 10000L)
        println("count: $count")
    }

    @Test
    fun findInfiniteScrollTest() {
        val articles01 = articleRepository.findAllInfiniteScroll(boardId = 1L, limit = 30L)
        articles01.forEach {
            println(it)
        }

        val lastArticleId = articles01.last().articleId
        val articles02 = articleRepository.findAllInfiniteScroll(
            boardId = 1L,
            limit = 30L,
            lastArticleId = lastArticleId
        )
        articles02.forEach {
            println(it)
        }
    }
}