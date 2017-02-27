package pro.absolutne.data

import java.util.*
import javax.persistence.*

//@Table(name = "record")
@Entity
data class RealEstateOffer(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val title: String,
        val url: String,
        val area: Double?,
        val price: Double?,
        val type: String,
        val location: String,
        val date: Date,
        val scrapSource: String,
        val scrapDate: Date)


