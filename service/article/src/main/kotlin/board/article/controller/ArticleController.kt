package board.article.controller

import board.article.service.ArticleService
import board.article.service.request.ArticleCreateRequest
import board.article.service.request.ArticleUpdateRequest
import board.article.service.response.ArticlePageResponse
import board.article.service.response.ArticleResponse
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController(
    private val articleService: ArticleService
) {

    @GetMapping("/v1/articles/{articleId}")
    fun read(@PathVariable articleId: Long): ArticleResponse =
        articleService.read(articleId)

    @GetMapping("/v1/articles")
    fun readAll(
        @RequestParam("boardId") boardId: Long,
        @RequestParam("page") page: Long,
        @RequestParam("pageSize") pageSize: Long,
    ): ArticlePageResponse =
        articleService.readAll(boardId, page, pageSize)

    @PostMapping("/v1/articles")
    fun create(@RequestBody article: ArticleCreateRequest): ArticleResponse =
        articleService.create(article)

    @PutMapping("/v1/articles/{articleId}")
    fun update(@PathVariable articleId: Long, article: ArticleUpdateRequest): ArticleResponse =
        articleService.update(articleId, article)

    @DeleteMapping("/v1/articles/{articleId}")
    fun delete(@PathVariable articleId: Long) =
        articleService.delete(articleId)
}