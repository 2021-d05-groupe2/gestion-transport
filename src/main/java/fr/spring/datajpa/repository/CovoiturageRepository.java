package fr.spring.datajpa.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.spring.datajpa.model.Collaborateur;
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

    @Query(value = "SELECT cv FROM Covoiturage cv JOIN FETCH cv.passagers psgr"
                + " WHERE psgr.mail=:mail"
                + " AND cv.date > CURRENT_DATE", nativeQuery = false)
    public List<Covoiturage> findAllCurrentCovoiturageCollaborateur(@Param("mail") String mail);

    @Query(value = "SELECT cv FROM Covoiturage cv JOIN FETCH cv.passagers psgr"
            + " WHERE psgr.mail=:mail"
            + " AND cv.date < CURRENT_DATE", nativeQuery = false)
    public List<Covoiturage> findAllPreviousCovoiturageCollaborateur(@Param("mail") String mail);

    @Override
    Optional<Covoiturage> findById(Long aLong);
    
    public List<Covoiturage> findByOrganisateur(Collaborateur orga);
}
