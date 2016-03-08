package nicemul.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
public class User {

	@Id
	@Column(name = "USER_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
	private long id;
	
	@Column(name = "FIRSTNAME", nullable = false, unique = true, length = 50)
	private String firstname;

	@Column(name = "LASTNAME", nullable = false, unique = true, length = 50)
	private String lastname;

	public User() {
		
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public User(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void merge(User p) {
		this.firstname = p.getFirstname();
		this.lastname = p.getLastname();
	}
	
}
