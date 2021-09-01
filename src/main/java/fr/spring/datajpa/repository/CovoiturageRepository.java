package fr.spring.datajpa.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.spring.datajpa.model.Covoiturage;

public interface CovoiturageRepository extends JpaRepository<Covoiturage, Long> {
    @Query(value = "SELECT cv FROM Covoiturage cv JOIN FETCH cv.passagers psgr WHERE psgr.mail=:mail", nativeQuery = false)
    public List<Covoiturage> findCovoiturageCollaborateur(@Param("mail") String mail);

    @Query(value = "SELECT cv FROM Covoiturage cv"
    			+ " WHERE cv.TravelStatus = 'CREATED'"
    			+ " AND cv.organisateur.id != :userId"
    			+ " AND cv.nbPassagers < cv.vehicule.totalPlaces"
			, nativeQuery = false)
    public List<Covoiturage> findAvailableCovoitsForUserID(@Param("userId") Long userId);

}