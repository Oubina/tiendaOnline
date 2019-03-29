package es.curso.registro.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import es.curso.registro.generic.GenericDaoHibernate;
import es.curso.registro.model.Role;

@Repository
public class RoleRepositoryImpl extends GenericDaoHibernate<Role, Long> implements RoleRepository  {
	
	public void deleteRolesUsersWithUserId (Long id) {
		final Session session = getSession();
		String sql = "delete from users_roles where user_id =:id";
		Query qry = session.createNativeQuery(sql);
		qry.setParameter("id", id);
		qry.executeUpdate();
	}
	
	
	public void deleteRolesUsersWithRoleIdUserId (Long idRole, Long idUser) {
		
		final Session session = getSession();
		String sql = "delete from users_roles where role_id =:idRole and user_id =:idUser ";
		Query <Role> qry = session.createNativeQuery(sql);
		qry.setParameter("idRole", idRole);
		qry.setParameter("idUser", idUser);		
		qry.executeUpdate();
	}
	
	
	public Role getRoleByName (String name) {
		final Session session = getSession();		

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Role> cr = cb.createQuery(Role.class);
		Root<Role> root = cr.from(Role.class);
		cr.select(root).where(cb.like(root.get("name"), name));

		Query<Role> query = session.createQuery(cr);
		
		return query.getSingleResult();
	}

}
