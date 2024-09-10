package co.com.test.linktic.appEcommerce.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Users {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id")
	 private Integer id;
	 
	 @Column(name = "name")
	 private String name;

	 @Column(name = "email")
	 private String email;
	 
	 @Column(name = "password")
	 private String password;

	 @Column(name = "phone")
	 private String phone;

	 @CreationTimestamp
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "date_creation", nullable = false, updatable = false)
	 private Date dateCreation;

	 @UpdateTimestamp
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "date_update", nullable = false)
	 private Date dateUpdate;
	    
}
