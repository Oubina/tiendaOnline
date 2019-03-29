package es.curso.registro.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import es.curso.registro.controller.dto.UserRegistrationDto;
import es.curso.registro.model.Role;
import es.curso.registro.model.User;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);
    User findById(Long id);

    User save(UserRegistrationDto registration);
    
    List<User> getAll ();

	public User update (User user);

	public void deleteRolesWithUserId (Long idUser);

	public void deleteRolesWithRoleIdUserId (Long idRole , Long idUser);
	
	public Role getRoleWithName (String name);

}
