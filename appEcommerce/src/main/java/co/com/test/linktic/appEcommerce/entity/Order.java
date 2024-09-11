package co.com.test.linktic.appEcommerce.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private Integer id;

	@Column(name = "date")
	private Date date;

	@Column(name = "customerName")
	private String customerName;

	@Column(name = "price")
	private Double totalAmount;

    @ManyToMany
    @JoinTable(
        name = "order_products", 
        joinColumns = @JoinColumn(name = "order_id"), 
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
	private List<Product> products;

}
