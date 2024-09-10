package co.com.test.linktic.appEcommerce.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	
	 @Id
	 @Column(name = "id_order")
	 private int id;
	 
	 @Column(name = "date")
	 private Date date;

	 @Column(name = "description")
	 private String description;

	 @Column(name = "price")
	 private String price;
	 
	 @Column(name = "stock")
	 private Date stock;
    
}
