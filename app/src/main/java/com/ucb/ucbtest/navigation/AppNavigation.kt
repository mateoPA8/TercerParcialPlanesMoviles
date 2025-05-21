package com.ucb.ucbtest.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ucb.domain.Movie
import com.ucb.ucbtest.counter.CounterUI
import com.ucb.ucbtest.gitalias.GitaliasUI
import com.ucb.ucbtest.login.LoginUI
import com.ucb.ucbtest.movie.MoviesUI
import com.ucb.ucbtest.moviedetail.MovieDetailUI
import com.ucb.ucbtest.screen.PromotionScreen
import com.ucb.ucbtest.takephoto.TakePhotoUI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.UUID

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var uuid = UUID.randomUUID().toString()
    // Write a message to the database
    val database = Firebase.database
    val myRef = database.getReference("app_promotion")
    var titlePromotion by remember { mutableStateOf("")  }
    var text = "(${uuid}) Hello, World! This is the best promotion"
    myRef.setValue(text)


    myRef.addValueEventListener( object: ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


        override fun onDataChange(p0: DataSnapshot) {
            val value = p0.getValue(String::class.java)
            titlePromotion = value.toString()
        }
    })

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }

    ) {
        composable(Screen.MenuScreen.route) {
            LoginUI(
                onSuccess = {
                    navController.navigate(Screen.GitaliasScreen.route)
                }
            )
        }

        composable(Screen.GitaliasScreen.route) {
            GitaliasUI()
        }

        composable(Screen.TakePhotoScreen.route) {
            TakePhotoUI()
        }

        composable(Screen.LoginScreen.route) {
            LoginUI(
                onSuccess = {
                    navController.navigate(Screen.GitaliasScreen.route)
                }
            )
        }

        composable(Screen.MoviesScreen.route) {

            MoviesUI( onSuccess = {
                movie ->
                    val movieJson = Json.encodeToString(movie)
                    val encodeMovieJson = URLEncoder.encode(movieJson, "UTF-8")
                    navController.navigate("${Screen.MovieDetailScreen.route}/$encodeMovieJson")
            })
        }

        composable(
            route = "${Screen.MovieDetailScreen.route}/{movie}",
            arguments = listOf(
                navArgument("movie") {
                    type = NavType.StringType
                }
            )
        ) {
            val movieJson = it.arguments?.getString("movie") ?: ""
            val movieDecoded = URLDecoder.decode(movieJson, "UTF-8")
            val movie = Json.decodeFromString<Movie>(movieDecoded)

            MovieDetailUI( movie = movie, onBackPressed = { navController.popBackStack() })
        }

        composable(Screen.CounterScreen.route) {
            CounterUI()
        }
        composable(Screen.PromotionScreen.route) {
            PromotionScreen(title = titlePromotion)
        }

    }


}