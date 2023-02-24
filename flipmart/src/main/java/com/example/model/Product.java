package com.example.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	@Column(name = "id")
	private String id;

	@Column(name = "name", nullable = false, columnDefinition = "nvarchar(50)")
	private String name;

	@Column(name = "price", nullable = false)
	private float price;

	@Column(name = "discount")
	private float discount;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "start_day_discount", nullable = true)
	private Date startDayDiscount;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "end_day_discount", nullable = true)
	private Date endDayDiscount;

	@Column(name = "views")
	private int views = 0;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "description", nullable = true, columnDefinition = "nvarchar(max)")
	private String description;

	@ManyToOne
	@JoinColumn(name = "id_brand")
	private Brand brand;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductColor> productColors;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductCategoryDetail> productCategoryDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ImageProduct> imageProducts;

	public Product(String name, float price, float discount, Date startDayDiscount, Date endDayDiscount, int quantity,
			String description, Brand brand) {
		super();
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.startDayDiscount = startDayDiscount;
		this.endDayDiscount = endDayDiscount;
		this.quantity = quantity;
		this.description = description;
		this.brand = brand;
	}

}
