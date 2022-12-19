package com.example.mymovie.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mymovie.model.Movie
import com.example.mymovie.model.getMovies

@Composable
fun DetailsScreen(navController: NavController,
                  movieId: String?){
    val newMovieList = getMovies().filter {
            movie -> movie.id == movieId
    }
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Transparent,
            elevation = 0.dp) {
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(140.dp))

                Text(text = "Movies", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

        }
    }) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {

                ExpandedMovieDetails(movie = newMovieList.first())

                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Movie Images")
                HorizontalScrollableImageView(newMovieList)
            }
        }
    }
}

@Composable
private fun HorizontalScrollableImageView(newMovieList: List<Movie>) {
    LazyRow {
        items(newMovieList[0].images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = image),
                    contentDescription = "Movie Poster"
                )
            }
        }
    }
}
@Preview
@Composable
fun ExpandedMovieDetails(movie: Movie = getMovies()[0]){
    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp) {

        Column(modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier
                    .padding(20.dp)
                    .size(230.dp),
                shape = RectangleShape,
                elevation = 4.dp
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = movie.poster).apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = "Image Poster"
                )
//                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Movie Image")
            }
            Text(
                text = movie.title,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Genre: ${movie.genre}",
                style = MaterialTheme.typography.caption
            )
            Text(
                text = "Released: ${movie.year}",
                style = MaterialTheme.typography.caption
            )
            Column {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.LightGray,
                        fontSize = 13.sp)
                    ){
                        append("Plot: ")
                    }
                    withStyle(style = SpanStyle(color = Color.LightGray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    )
                    ){
                        append(movie.plot)
                    }
                }, modifier = Modifier.padding(6.dp))

                Divider(modifier = Modifier.padding(3.dp))
                Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.caption)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.caption)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.caption)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}