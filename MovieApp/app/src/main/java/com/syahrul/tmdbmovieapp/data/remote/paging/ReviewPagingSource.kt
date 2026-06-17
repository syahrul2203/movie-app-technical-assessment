package com.syahrul.tmdbmovieapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.syahrul.tmdbmovieapp.data.mapper.toDomain
import com.syahrul.tmdbmovieapp.data.remote.api.TmdbApiService
import com.syahrul.tmdbmovieapp.domain.model.Review
import javax.inject.Inject

class ReviewPagingSource @Inject constructor(
    private val apiService: TmdbApiService,
    private val movieId: Int,
) : PagingSource<Int, Review>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getMovieReviews(movieId = movieId, page = page)
            val reviews = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = reviews,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages || reviews.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
