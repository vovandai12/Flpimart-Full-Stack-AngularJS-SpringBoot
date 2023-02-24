package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_category_detail")
public class ProductCategoryDetail {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	@Column(name = "id")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "id_category_detail")
	private CategoryDetail categoryDetail;

	public ProductCategoryDetail(Product product, CategoryDetail categoryDetail) {
		super();
		this.product = product;
		this.categoryDetail = categoryDetail;
	}
	
	
}
