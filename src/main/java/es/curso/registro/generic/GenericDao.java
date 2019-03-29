package es.curso.registro.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * The Interface GenericDao.
 *
 * @param <E>
 *            the element type
 * @param <K>
 *            the key type
 */
public interface GenericDao<E, K extends Serializable> {

	K save(E entity);

	void update(E entity);

	E get(K id);

	E load(K id);
	
	Set<E> load(List<K> ids);
	
	void remove(K id);

	void detach(E entity);

	void detach(Set<E> entities);
}
