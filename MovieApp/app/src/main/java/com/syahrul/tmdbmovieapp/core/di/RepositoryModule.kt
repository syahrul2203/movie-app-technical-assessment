package com.syahrul.tmdbmovieapp.core.di

import com.syahrul.tmdbmovieapp.data.repository.MovieRepositoryImpl
import com.syahrul.tmdbmovieapp.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl,
    ): MovieRepository
}
