package es.curso.registro.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;

/**
 * The Class GenericDaoHibernate.
 *
 * @param <E>
 *            the element type
 * @param <K>
 *            the key type
 */
@SuppressWarnings("unchecked")
public abstract class GenericDaoHibernate<E, K extends Serializable> implements GenericDao<E, K> {

	@PersistenceContext
	private EntityManager entityManger;

	/** The entity class. */
	private Class<E> entityClass;

	/**
	 * Instantiates a new generic dao hibernate.
	 */
	public GenericDaoHibernate() {
		this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	protected Session getSession() {
		return entityManger.unwrap(Session.class);
	}

	/**
	 * Creates the criteria query base.
	 *
	 * @param <T>
	 *            the generic type
	 * @param session
	 *            the session
	 * @param resultado
	 *            the resultado
	 * @return the criteria query
	 */
	protected <T> CriteriaQuery<T> createCriteriaQueryBase(final Session session, final Class<T> resultado) {
		return session.getCriteriaBuilder().createQuery(resultado);
	}

	@Override
	public K save(final E entity) {
		return (K) getSession().save(entity);
	}

	@Override
	public void update(final E entity) {
		getSession().update(entity);
	}

	@Override
	public E get(final K id) {
		return getSession().get(entityClass, id);
	}

	@Override
	public E load(final K id) {
		return getSession().load(entityClass, id);
	}
	
	@Override
	public Set<E> load(final List<K> ids) {
		Set<E> set = null;
		Session session = getSession();
		if (ids != null && !ids.isEmpty()) {
			set = new HashSet<>();
			for (K id : ids) {
				set.add(session.load(entityClass, id));
			}
		}
		
		return set;
	}

	@Override
	public void remove(final K id) {
		getSession().remove(get(id));
	}
	
	@Override
	public void detach(final E entity) {
		getSession().detach(entity);
	}
	
	@Override
	public void detach(final Set<E> entities) {
		Session session = getSession();
		if (entities != null && !entities.isEmpty()) {
			for (E entity : entities) {
				session.detach(entity);
			}
		}
	}
}
