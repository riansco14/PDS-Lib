package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Usuario;



public class UsuarioDAO extends GenericDAO<Usuario> {

	public Usuario localizar(String nome) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		Usuario usuario = null;
		
		try {

			t.begin();
			Query q = em.createQuery("from Usuario where nome like :nome");
			q.setParameter("nome", nome);
			usuario = (Usuario) q.getSingleResult();
			t.commit();

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();
		}

		return usuario;
	}

	public boolean inserir(Usuario usuario) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		boolean result = false;
		Usuario existentUsuario = null;
		
		try {

			t.begin();

			/* verifica se ja' existe um usuario com o mesmo nome */
			Query q = em.createQuery("from Usuario where nome like :nome");
			q.setParameter("nome", usuario.getNome());

			try {
				existentUsuario = (Usuario) q.getSingleResult();
			} catch(Exception e) { }
			
			/* se nao existe o usuario, persiste */
			if (existentUsuario == null) {
				em.persist(usuario);
			} else { /* ja' existe o usuario, somente retorna seu id */
				usuario.setIdUsuario(existentUsuario.getIdUsuario());
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
	
	public boolean update(long idUsuario, String senha) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		boolean result = false;
		
		try {

			t.begin();
			
			Usuario a=em.find(Usuario.class, idUsuario);
			a.setSenha(senha);
			
			
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
