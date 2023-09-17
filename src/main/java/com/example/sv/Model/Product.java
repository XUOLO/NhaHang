package com.example.sv.Model;

import java.beans.Transient;
import java.sql.Blob;
import java.time.LocalDateTime;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "Product")
public class Product {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;


	@NotBlank(message = "Đặc điểm không được để trống")
	  @Column(name = "Name")
	  private String name;

	@NotBlank(message = "Đặc điểm không được để trống")
	  @Column(name = "Description")
	  private String description;

	  @Lob
	  private Blob image;

	@Positive(message = "Đơn giá phải lớn hơn 0")
	  @Column(name = "Price")
	  private int price;
	  @NotBlank(message = "Đặc điểm không được để trống")
	  @Column(name = "Detail")
	  private String detail;




	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productCategory_id")
	private ProductCategory productCategory;

	private LocalDateTime createTime;

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusString() {
		switch (status) {
			case "1":
				return "Open";
			case "2":
				return "In Progress";
			case "3":
				return "Resolved";
			default:
				return "Unknown";
		}
	}
	public String getStatus() {
		return status;
	}

	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
}
