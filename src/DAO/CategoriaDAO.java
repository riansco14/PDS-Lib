package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Categoria;

public class CategoriaDAO extends GenericDAO<Categoria> {

	public Categoria localizar(String descricao) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		Categoria categoria = null;
		
		try {

			t.begin();
			Query q = em.createQuery(
					"from Categoria where descricao like :descricao");
			q.setParameter("descricao", descricao);
			categoria = (Categoria) q.getSingleResult();
			t.commit();

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();
		}

		return categoria;
	}
}
