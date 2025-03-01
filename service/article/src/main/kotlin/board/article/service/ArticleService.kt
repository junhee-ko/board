package board.article.service

import board.article.entity.Article
import board.article.repository.ArticleRepository
import board.article.service.request.ArticleCreateRequest
import board.article.service.request.ArticleUpdateRequest
import board.article.service.response.ArticlePageResponse
import board.article.service.response.ArticleResponse
import board.common.snowflake.Snowflake
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
) {
    private val snowFlake: Snowflake = Snowflake()

    @Transactional
    fun create(request: ArticleCreateRequest): ArticleResponse {
        val article = articleRepository.save(
            /* entity = */ Article.create(
                articleId = snowFlake.nextId(),
                title = request.title,
                content = request.content,
                boardId = request.boardId,
                writerId = request.writerId
            )
        )
        println(article)

        return ArticleResponse.from(article)
    }

    @Transactional
    fun update(articleId: Long, request: ArticleUpdateRequest): ArticleResponse {
        val article = articleRepository.findById(articleId).orElseThrow()
        val articleUpdated = article.update(request.title, article.content)

        return ArticleResponse.from(articleUpdated)
    }

    fun read(articleId: Long): ArticleResponse {
        val article = articleRepository.findById(articleId).orElseThrow()

        return ArticleResponse.from(article)
    }

    @Transactional
    fun delete(articleId: Long) {
        articleRepository.deleteById(articleId)
    }

    fun readAll(boardId: Long, page: Long, pageSize: Long): ArticlePageResponse {
        val articles = articleRepository.findAll(boardId = boardId, offset = (page - 1) * pageSize, limit = pageSize)
            .map { ArticleResponse.from(it) }
            .toList()

        val count = articleRepository.count(
            boardId = boardId,
            limit = PageLimitCalculator.calculatePageLimit(page = page, pageSize = pageSize, movablePageCount = 10L)
        )

        return ArticlePageResponse.of(articles, count)
    }

    fun readAllInfiniteScroll(boardId: Long, pageSize: Long, lastArticleId: Long?): List<ArticleResponse> {
        val articles = if (lastArticleId == null) {
            articleRepository.findAllInfiniteScroll(boardId, pageSize)
        } else {
            articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId)
        }

        return articles.map { ArticleResponse.from(it) }.toList()
    }
}