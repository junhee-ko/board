package board.article.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PageLimitCalculatorTest {

    @Test
    fun calculatePageLimitTest() {
        calculatePageLimitTest(page = 1, pageSize = 30L, movablePageCount = 10L, expected = 301L)
        calculatePageLimitTest(page = 7, pageSize = 30L, movablePageCount = 10L, expected = 301L)
        calculatePageLimitTest(page = 10, pageSize = 30L, movablePageCount = 10L, expected = 301L)
        calculatePageLimitTest(page = 11, pageSize = 30L, movablePageCount = 10L, expected = 601L)
        calculatePageLimitTest(page = 12, pageSize = 30L, movablePageCount = 10L, expected = 601L)
    }

    private fun calculatePageLimitTest(page: Long, pageSize: Long, movablePageCount: Long, expected: Long) {
        val result = PageLimitCalculator.calculatePageLimit(page, pageSize, movablePageCount)
        assertThat(result).isEqualTo(expected)
    }
}