package com.chat;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.repository.Repository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
// This will be AUTO IMPLEMENTED by Spring into a Bean 
// called userRepository
//@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{
	 Role findByRoleName(String roleName);
}
