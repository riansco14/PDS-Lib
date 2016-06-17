package DAO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class GenericDAO <T> {

	public static final boolean debugInfo = false;
	
	static EntityManagerFactory factory = null;
	private Class<T> classe;

	static {
		factory = Persistence.createEntityManagerFactory("Biblioteca");
	}

	@SuppressWarnings("unchecked")
	public GenericDAO(){

		Class<?> thisClass = getClass();
		if (debugInfo) {
			System.out.println(thisClass);
		}
		ParameterizedType t =
			(ParameterizedType) thisClass.getGenericSuperclass();
		Type t2 = t.getActualTypeArguments()[0];
		if (debugInfo) {
			System.out.println(t2);
		}
		this.classe = (Class<T>) t2;
	}

	public T localizar(long id) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		T obj = null;

		try {

			t.begin();
			obj = em.find(classe, id);
			t.commit();

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();
		}

		return obj;
	}

	@SuppressWarnings("unchecked")
	public List<T> listar() {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		List<T> list = null;

		try {

			t.begin();
			list = (List<T>) em.createQuery(
					"from " + classe.getSimpleName()).getResultList();
			t.commit();

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();
		}

		return list;
	}

	public boolean inserir(T obj) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		boolean result = false;
		
		try {

			t.begin();
			em.persist(obj);
			t.commit();
			result = true;

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();

		}
		
		return result;
	}

	public boolean excluir(long id) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		T obj = null;
		boolean result = false;

		try {

			t.begin();
			obj = em.find(classe, id);
			em.remove(obj);
			t.commit();
			result = true;

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();
		}
		
		return result;
	}
}
