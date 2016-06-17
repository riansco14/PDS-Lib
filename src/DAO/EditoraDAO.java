package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Editora;

public class EditoraDAO extends GenericDAO<Editora> {

	public Editora localizar(String nome) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		Editora editora = null;
		
		try {

			t.begin();
			Query q = em.createQuery("from Editora where nome like :nome");
			q.setParameter("nome", nome);
			editora = (Editora) q.getSingleResult();
			t.commit();

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();
		}

		return editora;
	}
}
