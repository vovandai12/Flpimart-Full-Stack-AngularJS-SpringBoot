package com.example.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.common.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	@Column(name = "id")
	private String id;

	@Column(name = "full_name", nullable = false, columnDefinition = "nvarchar(50)")
	private String fullName;

	@Column(name = "email", nullable = false, columnDefinition = "varchar(50)")
	private String email;

	@Column(name = "address", nullable = true, columnDefinition = "nvarchar(255)")
	private String address;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "birth_day", nullable = true)
	private Date birthDay;

	@Column(name = "gender", nullable = true, columnDefinition = "bit")
	private Boolean gender;

	@Column(name = "login", nullable = false, columnDefinition = "bit")
	private Boolean login;

	@Column(name = "avatar", nullable = true, columnDefinition = "varchar(max)")
	private String avatar;

	@Column(name = "user_name", nullable = false, columnDefinition = "varchar(50)")
	private String userName;

	@Column(name = "password", nullable = false, columnDefinition = "varchar(max)")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@Column(name = "last_login_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	public User(String userName, String fullName, String email, String password, Boolean login, Role role) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.login = login;
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	public User(String fullName, String email, String address, Date birthDay, Boolean login, Boolean gender,
			Role role) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.address = address;
		this.birthDay = birthDay;
		this.login = login;
		this.gender = gender;
		this.role = role;
	}

}
