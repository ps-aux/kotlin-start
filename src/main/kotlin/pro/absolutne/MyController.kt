package pro.absolutne

import JavaTest
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import pro.absolutne.data.RealEstateOffer
import pro.absolutne.data.RealEstateOfferJ
import pro.absolutne.data.RealEstateOfferRepository
import pro.absolutne.scrap.*
import java.util.*
import javax.transaction.Transactional

@RestController
class MyController(val repo: RealEstateOfferRepository) {

    var t = JavaTest()

    @GetMapping("/go")
    fun go() = "Yeah it works"

    @GetMapping("/java")
    fun test() = t.go()

    @GetMapping("/rec")
    fun rec() = repo.findAll().iterator().next()

    @GetMapping("/lombok")
    fun lombok(): Any {
        val l = RealEstateOfferJ()
//        l.setName("jano")

        return l
    }

    @GetMapping("/jpa")
    fun readJpa(): Any {
        val o = repo.findAll().iterator().next()

        println(o)
        return o
    }

    @PostMapping("/jpa")
    @Transactional
    fun saveJpa() {

        val l = RealEstateOffer(
                url = "some url",
                area = null,
                title = "testing title",
                date = Date(),
                location = "doma",
                scrapDate = Date(),
                scrapSource = "web",
                type = "typ",
                price = 123.0)


        repo.save(l)

    }


    @PostMapping("/start")
    fun start(): String {
        //        val browser = PhantomJSDriver()
        val driver = ChromeDriver()
        val filter = Filter(Action.BUY, FlatType.ALL)
        try {
            val job = TopRealityScrapJob(Browser(driver), filter, repo)
            job.start()
            println("Finished successfully")
        } catch (e: Exception) {
            println("Well, shit ...")
            e.printStackTrace()
        }
        println("Finished....")

        return "Done"
    }
}
