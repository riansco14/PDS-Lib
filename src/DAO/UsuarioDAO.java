package DAO;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Usuario;

public class UsuarioDAO {
	public void insert(Usuario usuario) {
		Session session = HibernateManager.getSession();
		Transaction tx = session.beginTransaction();
		session.save(usuario);
		tx.commit();
		session.close();
	}

	public void update(Usuario usuario) {
		Session session = HibernateManager.getSession();
		Transaction tx = session.beginTransaction();
		session.update(usuario);
		tx.commit();
		session.close();
	}

	public void delete(long CPF) {
		Session session = HibernateManager.getSession();
		 Transaction tx = session.beginTransaction();
		 Usuario usuario=new Usuario();
		 usuario.setCPF(CPF);
		 session.delete(usuario);
		 usuario=null;
		 tx.commit();
	}

	public List<Usuario> selectAll() {
		Session session = HibernateManager.getSession();

		List<Usuario> tmp = null;
		Transaction tx = session.beginTransaction();
		tmp = session.createCriteria(Usuario.class).list();
		tx.commit();
		session.close();
		return tmp;
	}

	public Usuario select(long userCPF) {
		Session session = HibernateManager.getSession();
		Usuario tmp = null;
		Serializable serializable=new Long(userCPF);
		tmp=(Usuario) session.get(Usuario.class, serializable);
		session.close();
		return tmp;
	}

}
