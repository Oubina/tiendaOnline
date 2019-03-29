package es.curso.registro.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.curso.registro.controller.dto.UserRegistrationDto;
import es.curso.registro.model.Role;
import es.curso.registro.model.User;
import es.curso.registro.repository.RoleRepository;
import es.curso.registro.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findById(Long id) {
		return userRepository.getOne(id);
	}

	public User save(UserRegistrationDto registration) {
		User user = new User();
		user.setNombre(registration.getNombre());
		user.setApellido(registration.getApellido());
		user.setEmail(registration.getEmail());
		user.setPassword(passwordEncoder.encode(registration.getPassword()));
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Usuario o contraseña incorrectos");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

	public List<User> getAll() {

		return userRepository.findAll();
	}

	public User update (User user) {
		return userRepository.save(user);
	}
 
	@Transactional
	public void deleteRolesWithUserId (Long idUser) {
		
		roleRepository.deleteRolesUsersWithUserId ( idUser);
	}

	@Transactional
	public void deleteRolesWithRoleIdUserId (Long idRole , Long idUser) {
		
		roleRepository.deleteRolesUsersWithRoleIdUserId(idRole, idUser);
	}

	public Role getRoleWithName (String name) {
		return roleRepository.getRoleByName (name);
	}


}
