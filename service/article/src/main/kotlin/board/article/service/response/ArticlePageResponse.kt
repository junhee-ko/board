package board.article.service.response

data class ArticlePageResponse(
    val articles: List<ArticleResponse>,
    val articleCount:Long
) {

    companion object {
        fun of(articles: List<ArticleResponse>, articleCount: Long): ArticlePageResponse =
            ArticlePageResponse(
                articles = articles,
                articleCount = articleCount
            )
    }
}