package io.kaendagger.apodcompanion

import io.kaendagger.apodcompanion.data.model.ApodOffline

object DummyApodFactory {
    private val dummyApodExplanation1 = """
        Frank Borman, James Lovell, and William Anders journeyed from Earth to the Moon and back again in December of 1968.
        From lunar orbit, their view of craters in southwest Mare Fecunditatis is featured in this stereo anaglyph, 
        best experienced from armchairs on planet Earth with red/blue glasses. Goclenius is the large impact crater in the foreground.
        About 70 kilometers (45 miles) in diameter its lava-flooded floor is scarred by rilles or grooves, long, narrow depressions in the surface.
        Crossing the crater walls and central peaks the rilles were likely formed after the crater itself. In the background,
        the two large craters with smooth floors are Colombo A (top) and Magelhaens. Magelhaens A, the background crater 
        with the irregular floor, is about 35 kilometers (20 miles) in diameter.
    """.trimIndent()

    fun makeDummyApodOffline() = ApodOffline(
        "2019-10-04",
        dummyApodExplanation1,
        "Southwest Mare Fecunditatis",
        "dummy/path/to/apods"
    )

}