package com.ucb.ucbtest.di

import android.content.Context
import com.ucb.data.GithubRepository
import com.ucb.data.LibroRepository
import com.ucb.data.LoginRepository
import com.ucb.data.MovieRepository
import com.ucb.data.PlanesRemoteDataSourceImpl

import com.ucb.data.PlanesRepository
import com.ucb.data.PushNotificationRepository
import com.ucb.data.datastore.ILoginDataStore
import com.ucb.data.git.IGitRemoteDataSource
import com.ucb.data.git.ILocalDataSource
import com.ucb.data.libro.ILibroRemoteDataSource
import com.ucb.data.libro.ILocalDataSources
import com.ucb.data.movie.IMovieRemoteDataSource
import com.ucb.data.plan.IPlanesRemoteDataSource
import com.ucb.data.push.IPushDataSource
import com.ucb.framework.github.GithubLocalDataSource
import com.ucb.framework.github.GithubRemoteDataSource
import com.ucb.framework.movie.MovieRemoteDataSource
import com.ucb.framework.service.RetrofitBuilder
import com.ucb.ucbtest.R
import com.ucb.usecases.DoLogin
import com.ucb.usecases.FindGitAlias
import com.ucb.usecases.GetPopularMovies
import com.ucb.usecases.SaveGitalias
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.ucb.framework.datastore.LoginDataSource
import com.ucb.framework.libro.LibroLocalDataSource
import com.ucb.framework.libro.LibroRemoteDataSource
import com.ucb.framework.push.FirebaseNotificationDataSource
import com.ucb.usecases.BuscarLibros
import com.ucb.usecases.GetEmailKey
import com.ucb.usecases.GuardarLibro
import com.ucb.usecases.ObtainToken
import com.ucb.usecases.ObtenerPlanes


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerRetrofitBuilder(@ApplicationContext context: Context) : RetrofitBuilder {
        return RetrofitBuilder(context)
    }


    @Provides
    @Singleton
    fun gitRemoteDataSource(retrofiService: RetrofitBuilder): IGitRemoteDataSource {
        return GithubRemoteDataSource(retrofiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): ILocalDataSource {
        return GithubLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun gitRepository(remoteDataSource: IGitRemoteDataSource, localDataSource: ILocalDataSource): GithubRepository {
        return GithubRepository(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideSaveGitAlias(repository: GithubRepository): SaveGitalias {
        return SaveGitalias(repository)
    }

    @Provides
    @Singleton
    fun provideGitUseCases(githubRepository: GithubRepository): FindGitAlias {
        return FindGitAlias(githubRepository)
    }

    @Provides
    @Singleton
    fun provideGetPopularMovies(movieRepository: MovieRepository, @ApplicationContext context: Context): GetPopularMovies {
        val token = context.getString(R.string.token)
        return GetPopularMovies(movieRepository, token)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(dataSource: IMovieRemoteDataSource) : MovieRepository {
        return MovieRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(retrofit: RetrofitBuilder ): IMovieRemoteDataSource {
        return MovieRemoteDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun provideDoLogin(loginRepository: LoginRepository): DoLogin {
        return DoLogin(loginRepository)
    }

    @Provides
    @Singleton
    fun provideLoginRepository( loginDataSource: ILoginDataStore): LoginRepository {
        return LoginRepository(loginDataSource)
    }

    @Provides
    @Singleton
    fun provideLoginDataSource( @ApplicationContext context: Context ): ILoginDataStore {
        return LoginDataSource(context = context)
    }

    @Provides
    @Singleton
    fun provideGetEmailKey(loginRepository: LoginRepository): GetEmailKey {
        return GetEmailKey(loginRepository)
    }

    @Provides
    @Singleton
    fun provideObtainToken( pushNotificationRepository: PushNotificationRepository): ObtainToken {
        return ObtainToken(pushNotificationRepository)
    }

    @Provides
    @Singleton
    fun providePushNotificationRepository( pushDataSource: IPushDataSource): PushNotificationRepository {
        return PushNotificationRepository(pushDataSource)
    }

    @Provides
    @Singleton
    fun provideIPushDataSource(): IPushDataSource {
        return FirebaseNotificationDataSource()
    }
    @Provides
    @Singleton
    fun provideLibroRepository(remoteDataSource: ILibroRemoteDataSource): LibroRepository {
        return LibroRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideBuscarLibros(libroRepository: LibroRepository): BuscarLibros {
        return BuscarLibros(libroRepository)
    }
    @Provides
    @Singleton
    fun provideLibroRemoteDataSource(retrofitBuilder: RetrofitBuilder): ILibroRemoteDataSource {
        return LibroRemoteDataSource(retrofitBuilder)
    }
    @Provides
    @Singleton
    fun provideLocalDataSources(@ApplicationContext context: Context): ILocalDataSources {
        return LibroLocalDataSource(context)
    }
    @Provides
    @Singleton
    fun provideGuardarLibro(localDataSource: ILocalDataSources): GuardarLibro {
        return GuardarLibro(localDataSource)
    }
    @Provides
    @Singleton
    fun providePlanesRepository(remoteDataSource: IPlanesRemoteDataSource): PlanesRepository {
        return PlanesRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideObtenerPlanes(repository: PlanesRepository): ObtenerPlanes {
        return ObtenerPlanes(repository)
    }
    @Provides
    @Singleton
    fun providePlanesRemoteDataSource(): IPlanesRemoteDataSource {
        return PlanesRemoteDataSourceImpl()
    }
}