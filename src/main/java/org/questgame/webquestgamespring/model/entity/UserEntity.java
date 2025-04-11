package org.questgame.webquestgamespring.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String username;

	@Column(length = 100)
	private String password;

	@ManyToMany
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<RoleEntity> roles;

	public UserEntity(String username, String password, List<RoleEntity> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public UserEntity(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
