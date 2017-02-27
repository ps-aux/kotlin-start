package pro.absolutne.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface RealEstateOfferRepository : CrudRepository<RealEstateOfferJ, Long> {

    @Transactional
    override fun <S : RealEstateOfferJ?> save(entity: S): S
}

