package co.com.test.linktic.appEcommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	 @Id
	 @Column(name = "id_product")
	 private int id;
	 
	 @Column(name = "name")
	 private String name;

	 @Column(name = "description")
	 private String description;

	 @Column(name = "price")
	 private Double price;
	 
	 @Column(name = "stock")
	 private Integer stock;
	 
	 @ManyToOne()
	 @JoinColumn(name = "id_category", nullable = false)
	 private Category category;
    
}
