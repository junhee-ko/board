package board.article.service.response

import board.article.entity.Article
import java.time.LocalDateTime

data class ArticleResponse(
    val articleId: Long,
    val title: String,
    val content: String,
    val boardId: Long, // shard key
    val writerId: Long,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {

    companion object {
        fun from(article: Article): ArticleResponse =
            ArticleResponse(
                articleId = article.articleId,
                title = article.title,
                content = article.content,
                boardId = article.boardId,
                writerId = article.writerId,
                createdAt = article.createdAt,
                modifiedAt = article.modifiedAt
            )
    }
}