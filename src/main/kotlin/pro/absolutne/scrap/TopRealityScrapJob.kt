package pro.absolutne.scrap

import pro.absolutne.data.RealEstateOffer
import pro.absolutne.data.RealEstateOfferRepository
import java.text.SimpleDateFormat
import java.util.*

class TopRealityScrapJob(private val b: Browser,
                         private val filter: Filter,
                         private val repo: RealEstateOfferRepository) {

    private val takeDigits = "[\\d ]+".toRegex()
    private val takeFlatType = "(\\d\\s.*byt|.*[Gg]ars.*)\\s(.*)\\s(predaj)".toRegex()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy")


    fun start() {
        b.goTo("https://www.topreality.sk/")
        selectFilter()
        var pageNo = 1

        val paginator = ".paginator .next"

        do {
            println(pageNo)
            val page = b.find(".listing")
            page.findAll(".estate")
                    .map { extractRecord(it) } //TODO without (it) ?
                    .filterNotNull()
                    .forEach { process(it) }

            b.find(paginator).click()
            pageNo += 1
        } while (b.isPresent(paginator))
        println("No paginator found. Done.")

    }

    private fun process(r: RealEstateOffer) = repo.save(r)

    private fun extractRecord(el: BrowserElement): RealEstateOffer? {

        try {

            fun parseNum(str: String): Double? {
                val replaced = "\\s".replace(str, "")
                return takeDigits.find(replaced)?.value?.toDouble()
            }


            val header = el.find("h2 a")
            val area = b.tryFind(".areas strong")
            val locality = el.find(".locality")
            val price = el.find(".price")
            val date = el.find(".date")

            val typeLocationActionInfo = el.find(".links li")

            val type = takeFlatType.find(typeLocationActionInfo.text)?.toString()
                    ?: "N/A"

            val priceVal = parseNum(price.text)

            val areaVal = if (area.isPresent)
                parseNum(area.get().text) else null

            return RealEstateOffer(title = header.text,
                    url = header.getAttribute("href"),
                    area = areaVal,
                    price = priceVal,
                    location = locality.text,
                    date = dateFormat.parse(date.text),
                    type = type,
                    scrapSource = "top-reality",
                    scrapDate = Date()
            )


        } catch(e: Exception) {
            val v = el.text
            println("Problem with $v")
            e.printStackTrace()
            return null
        }
    }

    private fun selectFilter() {
        val form = b.find("#searchform")

        // Setup predaj as the action
        b.selectMatching(form.find("#n_srch_typ"), "Predaj")

        addFlatTypes(form)
        addLocation(form)
        submit(form)

    }

    private fun addFlatTypes(form: BrowserElement) {
        // Make it apear
        form.find("#n_srch_druh + button").click()

        filter.flatTypes.forEach { addFlatType(it) }

        // Close
        form.find("label[for=n_srch_druh]").click() // Click to close the menu
    }

    private fun addFlatType(flatType: FlatType) {

        fun getTitle(roomCount: Int): String = when (roomCount) {
            0 -> "garzónka"
            else -> "$roomCount izbový byt"
        }

        val select = when (flatType) {
            FlatType.ALL -> ".ui-multiselect-optgroup-label a"
            else -> "input[name=multiselect_n_srch_druh][title='${getTitle(flatType.roomsNo)}']"
        }

        b.find(select).click()
    }

    private fun addLocation(form: BrowserElement) {
        form.find("#token-input-n_srch_obec").sendKeys("Bratislava")

        val l = b.findVisible(".token-input-dropdown-topreality ul")

        l.findAll("li").find({ it.text.contains("obec") })!!.click()
    }

    private fun submit(form: BrowserElement) {
        form.find("button.submit").click()
    }


}