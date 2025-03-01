package board.article.api

import board.article.service.request.ArticleCreateRequest
import board.article.service.request.ArticleUpdateRequest
import board.article.service.response.ArticlePageResponse
import board.article.service.response.ArticleResponse
import org.junit.jupiter.api.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.client.RestClient

class ArticleApiTest {

    private val restClient: RestClient = RestClient.create("http://localhost:9000")

    @Test
    fun createTest() {
        val response = create(
            ArticleCreateRequest(
                title = "hi",
                content = "my-content",
                writerId = 1L,
                boardId = 1L
            )
        )

        println("response: $response")
    }

    private fun create(request: ArticleCreateRequest): ArticleResponse? =
        restClient.post()
            .uri("/v1/articles")
            .body(request)
            .retrieve()
            .body(ArticleResponse::class.java)

    @Test
    fun readTest() {
        val response = read(153767517265334272)
        println("response: $response")
    }

    private fun read(articleId: Long): ArticleResponse? =
        restClient.get()
            .uri("/v1/articles/$articleId")
            .retrieve()
            .body(ArticleResponse::class.java)

    @Test
    fun updateTest() {
        update(153767517265334272)
        val response = read(153767517265334272)
        println(response)
    }

    private fun update(articleId: Long) {
        restClient.put()
            .uri("/v1/articles/$articleId")
            .body(ArticleUpdateRequest("this is title", "this is content"))
            .retrieve()
    }

    @Test
    fun deleteTest() {
        restClient.delete()
            .uri("/v1/articles/{articleId}", 153768678928158720)
            .retrieve()
    }

    @Test
    fun readAllTest() {
        val articlePageResponse = restClient.get()
            .uri("/v1/articles?boardId=1&page=30&pageSize=1")
            .retrieve()
            .body(ArticlePageResponse::class.java)

        println(articlePageResponse?.articleCount)
        articlePageResponse?.articles?.forEach {
//            println("article: $it")
        }
    }

    @Test
    fun readAllInfiniteScrollTest() {
        val articles01: List<ArticleResponse>? = restClient.get()
            .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5")
            .retrieve()
            .body(object : ParameterizedTypeReference<List<ArticleResponse>>() {})

        println("first page")

        articles01?.forEach {
            println("article id: ${it.articleId}")
        }

        val lastArticleId = articles01?.last()?.articleId
        val articles02: List<ArticleResponse>? = restClient.get()
            .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5&lastsArticleId=$lastArticleId")
            .retrieve()
            .body(object : ParameterizedTypeReference<List<ArticleResponse>>() {})

        println("second page")
        articles02?.forEach {
            println("article id: ${it.articleId}")

        }
    }
}