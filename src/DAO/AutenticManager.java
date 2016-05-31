package DAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import model.Usuario;

public class AutenticManager {
	public boolean autenticarUsuario(long userCPF, String senha) {
		Session session = HibernateManager.getSession();
		Criteria crit = session.createCriteria(Usuario.class);
		crit.add(Restrictions.eq("CPF", userCPF)).add(Restrictions.eq("senha", senha));
		if (crit.uniqueResult() == null)
			return false;
		return true;
	}
}
