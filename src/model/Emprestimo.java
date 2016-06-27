package model;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tb_emprestimo")
public class Emprestimo {

	@EmbeddedId
	private EmprestimoID emprestimoID;
	
	@Temporal(value=TemporalType.DATE)
	private Date dtEmprestimo;

	@Temporal(value=TemporalType.DATE)
	private Date dtDevolucao;
	
	
	public Emprestimo() { }
	
	public EmprestimoID getEmprestimoID() {
		return emprestimoID;
	}
	
	public void setEmprestimoID(EmprestimoID emprestimoID) {
		this.emprestimoID = emprestimoID;
	}
	
	public Date getDtEmprestimo() {
		return dtEmprestimo;
	}
	
	public void setDtEmprestimo(Date dtEmprestimo) {
		this.dtEmprestimo = dtEmprestimo;
	}

	public Date getDtDevolucao() {
		return dtDevolucao;
	}
	
	public void setDtDevolucao(Date dtDevolucao) {
		this.dtDevolucao = dtDevolucao;
	}
	
}
