package es.curso.registro.repository;

import es.curso.registro.generic.GenericDao;
import es.curso.registro.model.Role;


public interface RoleRepository extends GenericDao<Role, Long>{
	void deleteRolesUsersWithUserId (Long id);
	
	void deleteRolesUsersWithRoleIdUserId (Long idRole, Long idUser);
	
	Role getRoleByName (String name);
}
