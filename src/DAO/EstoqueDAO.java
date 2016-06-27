package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Estoque;
import model.Livro;

public class EstoqueDAO extends GenericDAO<Estoque> {

	public int buscarQuantidade(Livro livro) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		int result = 0;

		try {

			t.begin();

			Query q = em.createQuery("from Estoque where isbnLivro = :isbn");
			q.setParameter("isbn", livro.getIsbn());
			result = q.getResultList().size();

			t.commit();

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

	public boolean inserir(Livro livro, int quantidade) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		boolean result = false;
		Estoque estoque = null;
		
		try {

			t.begin();

			livro = em.find(Livro.class, livro.getIsbn());
			
			for (int i = 0; i < quantidade; i++) {
				
				estoque = new Estoque();
				estoque.setLivro(livro);
				em.persist(estoque);
			}
			
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

	public boolean excluir(String isbn, int quantidade) {
		
		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		boolean result = false;
		List<Estoque> estoqueItems = null;
		Livro livro = null;
		Estoque estoque = null;

		try {

			t.begin();

			livro = em.find(Livro.class, isbn);
			estoqueItems = livro.getEstoqueItems();
			
			while (quantidade > 0 && estoqueItems.size() != 0) {
				
				estoque = estoqueItems.get(estoqueItems.size() - 1);
				estoqueItems.remove(estoque);
				em.remove(estoque);
				quantidade--;
			}
			
			em.merge(livro);
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
