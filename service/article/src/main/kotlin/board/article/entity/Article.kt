package board.article.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Article(
    @Id val articleId: Long,
    var title: String,
    var content: String,
    val boardId: Long, // shard key
    val writerId: Long,
    val createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime,
) {

    fun update(title: String, content: String): Article {
        this.title = title
        this.content = content
        this.modifiedAt = LocalDateTime.now()

        return this
    }

    companion object {
        fun create(articleId: Long, title: String, content: String, boardId: Long, writerId: Long): Article =
            Article(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                writerId = writerId,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now()
            )
    }
}