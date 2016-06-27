	package DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Emprestimo;
import model.EmprestimoID;
import model.Estoque;
import model.Livro;
import model.Usuario;


public class EmprestimoDAO extends GenericDAO<Emprestimo> {

	public boolean retirar(Livro livro, Usuario usuario) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		
		boolean result = false;

		EmprestimoID emprestimoID = new EmprestimoID();
		Emprestimo emprestimo = new Emprestimo();
		
		try {

			t.begin();
			
			livro   = em.find(Livro.class, livro.getIsbn());
			usuario = em.find(Usuario.class, usuario.getIdUsuario());
			
			List<Estoque> estoqueItems = livro.getEstoqueItems(); 
			Estoque estoque = estoqueItems.get(estoqueItems.size() - 1);
			estoqueItems.remove(estoque);
			
			emprestimoID.setLivro(livro);
			emprestimoID.setUsuario(usuario);
			
			emprestimo.setEmprestimoID(emprestimoID);
			emprestimo.setDtEmprestimo(new Date());
			
			em.remove(estoque);
			em.persist(emprestimo);
			
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

	public boolean devolver(Livro livro, Usuario usuario) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		
		boolean result = false;

		Estoque estoque = new Estoque();
		EmprestimoID emprestimoID = new EmprestimoID();
		Emprestimo emprestimo = null;
		
		
		try {

			t.begin();
			
			livro   = em.find(Livro.class, livro.getIsbn());
			usuario = em.find(Usuario.class, usuario.getIdUsuario());
			
			emprestimoID.setLivro(livro);
			emprestimoID.setUsuario(usuario);
			
			emprestimo = em.find(Emprestimo.class, emprestimoID);
			
			/* verifica se livro nao foi devolvido ainda */
			if (emprestimo.getDtDevolucao() == null) { 
		
				emprestimo.setDtDevolucao(new Date());

				estoque.setLivro(livro);
				List<Estoque> estoqueItems = livro.getEstoqueItems(); 
				estoqueItems.add(estoque);
			
				em.persist(estoque);
				em.persist(emprestimo);
				
				result = true;
				
			} else { /* livro ja' devolvido */
				
				result = false;
			}
			
			t.commit();

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();
			result = false;

		} finally {

			em.close();

		}
		
		return result;
	}

	public Emprestimo consultar(Livro livro, Usuario usuario) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		
		Emprestimo result = null;
		EmprestimoID emprestimoID = new EmprestimoID();
		
		try {

			t.begin();
			
			livro   = em.find(Livro.class, livro.getIsbn());
			usuario = em.find(Usuario.class, usuario.getIdUsuario());
			
			emprestimoID.setLivro(livro);
			emprestimoID.setUsuario(usuario);
			
			result = em.find(Emprestimo.class, emprestimoID);

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

	@SuppressWarnings("unchecked")
	public List<Livro> localizar(Usuario usuario) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();

		List<Livro> result = new ArrayList<Livro>();
		List<Emprestimo> emprestimoItems = null;
		
		try {

			t.begin();

			Query q = em.createQuery(
				"from Emprestimo where emprestimoID.usuario.idUsuario = :id");
			q.setParameter("id", usuario.getIdUsuario());
			emprestimoItems = (List<Emprestimo>) q.getResultList();
			
			for (Emprestimo emprestimo : emprestimoItems) {
				
			if(emprestimo.getDtDevolucao()==null)
				result.add(emprestimo.getEmprestimoID().getLivro());
			}

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
	
	@SuppressWarnings("unchecked")
	public List<Usuario> localizar(Livro livro) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();

		List<Usuario> result = new ArrayList<Usuario>();
		List<Emprestimo> emprestimoItems = null;
		
		try {

			t.begin();

			Query q = em.createQuery(
					"from Emprestimo where emprestimoID.livro.isbn = :isbn");
			q.setParameter("isbn", livro.getIsbn());
			emprestimoItems = (List<Emprestimo>) q.getResultList();
			
			for (Emprestimo emprestimo : emprestimoItems) {

				result.add(emprestimo.getEmprestimoID().getUsuario());
			}

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
}
