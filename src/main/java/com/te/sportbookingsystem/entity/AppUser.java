package com.te.sportbookingsystem.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity

public class AppUser{

	@Id
	private String userName;
	private String passWord;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "map_users_roles",
	joinColumns = @JoinColumn(name="user_fk"),
	inverseJoinColumns = @JoinColumn(name="role_fk"))
	private List<Roles> roles = Lists.newArrayList();
}
