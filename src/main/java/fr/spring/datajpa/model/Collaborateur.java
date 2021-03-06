package fr.spring.datajpa.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import fr.spring.datajpa.enums.Role;

@Entity
public class Collaborateur extends AbstractUser {

	@ManyToMany(mappedBy = "passagers")
	@JsonIgnore
	private Set<Covoiturage> travels;
	
	@OneToMany(mappedBy="organisateur")
	@JsonManagedReference(value="organisateur")
	private Set<Covoiturage> annoncesPubliees;

	@OneToMany(mappedBy="organisateur")
	@JsonManagedReference(value="organisateur")
	private Set<Reservation> reservations;
	
	public Collaborateur() {
		travels = new HashSet<Covoiturage>();
		annoncesPubliees = new HashSet<Covoiturage>();
		reservations = new HashSet<Reservation>();
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Set<Covoiturage> getTravels() {
		return travels;
	}

	public Set<Covoiturage> getAnnoncePubliees() {
		return annoncesPubliees;
	}

	@Override
	public Role getRole() {
		return Role.COLLABORATEUR;
	}

	// Return the first found concurrent travel of given date and duration, return null is no result
	public AbstractTravel getConcurrentTravel(LocalDateTime dateDepart, int dureeMinutes) {
		
		Set<AbstractTravel> allTravels = new HashSet<AbstractTravel>();
		allTravels.addAll(annoncesPubliees);
		allTravels.addAll(travels);
		
		LocalDateTime dateArrivee = dateDepart.plusMinutes(dureeMinutes);
		for(AbstractTravel travel : allTravels) {
			LocalDateTime tDateDepart = travel.getDate();
			LocalDateTime tDateArrivee = tDateDepart.plusMinutes(travel.getDuree());
			
			if(dateDepart.isEqual(tDateDepart) || dateDepart.isEqual(tDateArrivee)
					|| dateArrivee.isEqual(tDateDepart) || dateArrivee.isEqual(tDateArrivee)) {
				return travel;
			}
			
			if((dateDepart.isBefore(tDateArrivee) && dateDepart.isAfter(tDateDepart)
					|| dateArrivee.isBefore(tDateArrivee) && dateArrivee.isAfter(tDateDepart))
			 || (tDateDepart.isBefore(dateArrivee) && tDateDepart.isAfter(dateDepart)
				|| tDateArrivee.isBefore(dateArrivee) && tDateArrivee.isAfter(dateDepart))) {
				return travel;
			}
		}
		return null;
	}
	
}
