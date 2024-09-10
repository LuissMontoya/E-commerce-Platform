package co.com.test.linktic.appEcommerce.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	
	 @Id
	 @Column(name = "id_category")
	 private int id;
	 
	 @Column(name = "name")
	 private String name;
	 
	 @Column(name = "status")
	 private String status;

}
