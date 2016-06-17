package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Categoria;
import model.Editora;
import model.Livro;


public class LivroDAO extends GenericDAO<Livro> {
	
	public Livro localizar(String isbn) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		Livro livro = null;
		
		try {

			t.begin();
			Query q = em.createQuery("from Livro where isbn = :isbn");
			q.setParameter("isbn", isbn);
			livro = (Livro) q.getSingleResult();
			t.commit();

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();
		}

		return livro;
	}
	
	public List<Livro> localizarTitle(String titulo) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		List<Livro> livro = null;
		
		try {

			t.begin();
			Query q = em.createQuery("from Livro where titulo like :isbn");
			q.setParameter("isbn", "%"+titulo+"%");
			livro =  q.getResultList();
			t.commit();

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();
		}

		return livro;
	}
	

	public boolean inserir(Livro livro, Editora editora) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		boolean result = false;
		Categoria existentCategoria = null;
		Editora existentEditora = null;
		Query q = null;
		
		try {

			t.begin();

			/* verifica se ja' existe uma categoria com a mesma descricao */
			q = em.createQuery("from Categoria where descricao like :descricao");
			q.setParameter("descricao", livro.getCategoria().getDescricao());

			try {
				existentCategoria = (Categoria) q.getSingleResult();
				if (existentCategoria != null) {
					livro.setCategoria(existentCategoria);
				}
			} catch(Exception e) { }
			
			/* verifica se ja' existe uma editora com o mesmo nome */
			q = em.createQuery("from Editora where nome like :nome");
			q.setParameter("nome", livro.getEditora().getNome());

			try {
				existentEditora = (Editora) q.getSingleResult();
				if (existentEditora != null) {
					editora = existentEditora;
				}
			} catch(Exception e) { }
			
			livro.setEditora(editora);
			
			em.persist(livro);
			
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

	public boolean excluir(String isbn) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		Livro livro = null;
		boolean result = false;

		try {

			t.begin();
			Query q = em.createQuery("from Livro where isbn = :isbn");
			q.setParameter("isbn", isbn);
			
			livro = (Livro) q.getSingleResult();
			em.remove(livro);
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
