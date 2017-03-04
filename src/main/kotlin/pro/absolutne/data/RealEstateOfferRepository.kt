package pro.absolutne.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface RealEstateOfferRepository : CrudRepository<RealEstateOffer, Long> {

    @Transactional
    override fun <S : RealEstateOffer?> save(entity: S): S
}

