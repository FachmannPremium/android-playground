package lt.ro.fachmann.lab2.data

import lt.ro.fachmann.lab2.R
import java.math.BigInteger
import java.util.*

/**
 * Created by bartl on 27.04.2017.
 */
object MoviesDataBase {
    private var init: Boolean = false
    private val random: Random = Random()
    val movieList = prepareMovieData()
    fun prepareMovieData(): ArrayList<Movie> {
        val actorSet = mutableListOf(
                Actor("Christian", "Bale", R.drawable.actor1),
                Actor("Jared", "Leto", R.drawable.actor2),
                Actor("Al", "Pacino", R.drawable.actor3),
                Actor("Sean", "Penn", R.drawable.actor4))
        val posterSet = listOf(R.drawable.poster1, R.drawable.poster2, R.drawable.poster3, R.drawable.poster4, R.drawable.poster5)
        val imageSet = mutableListOf(R.drawable.movie_random1, R.drawable.movie_random2, R.drawable.movie_random3, R.drawable.movie_random4, R.drawable.movie_random5, R.drawable.movie_random6, R.drawable.movie_random7, R.drawable.movie_random8)
        if (init) return movieList
        val words = 150
        val movies = arrayListOf(
                Movie("American Psycho", "Action & Adventure", "2000", "A wealthy New York investment banking executive hides his alternate psychopathic ego from his co-workers and friends as he delves deeper into his violent, hedonistic fantasies.",
                        listOf(actorSet[0], actorSet[1], actorSet[3]), R.drawable.poster,
                        listOf(R.drawable.movie_image1, R.drawable.movie_image2, R.drawable.movie_image3, R.drawable.movie_image4, R.drawable.movie_image5, R.drawable.movie_image6),
                        true, 5.0f),
                Movie("Mad Max: Fury Road", "Action & Adventure", "2015", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Inside Out", "Animation, Kids & Family", "2015", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Shaun the Sheep", "Animation", "2015", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("The Martian", "Science Fiction & Fantasy", "2015", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Mission: Impossible Rogue Nation", "Action", "2015", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Up", "Animation", "2009", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Star Trek", "Science Fiction", "2009", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("The LEGO Movie", "Animation", "2014", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Iron Man", "Action & Adventure", "2008", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Aliens", "Science Fiction", "1986", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Chicken Run", "Animation", "2000", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Back to the Future", "Science Fiction", "1985", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Raiders of the Lost Ark", "Action & Adventure", "1981", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Goldfinger", "Action & Adventure", "1965", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2),
                Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014", randomString(words), ArrayList(shuffle(actorSet)).subList(0, 3), posterSet[random.nextInt(posterSet.size)],
                        ArrayList(shuffle(imageSet)).subList(0, 6), random.nextBoolean(), random.nextInt(10).toFloat() / 2)
        )
        movies.sortWith(compareBy({ it.year }))
        init = true
        return movies
    }

    fun <T> shuffle(items: MutableList<T>): List<T> {
        val rg: Random = Random()
        for (i in 0..items.size - 1) {
            val randomPosition = rg.nextInt(items.size)
            val tmp: T = items[i]
            items[i] = items[randomPosition]
            items[randomPosition] = tmp
        }
        return items
    }

    fun randomString(words: Int) =
            IntArray(words) { random.nextInt(70) }.asList().map { i -> randomWord(i) }.joinToString(" ")


    fun randomWord(length: Int): String {
        return BigInteger(length, random).toString(32)
    }
}