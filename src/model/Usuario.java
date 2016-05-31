package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Usuario {

	
	private long CPF;
	private String nome;
	private int debito;
	@Column(name="senha")
	private String senha;
	public Usuario() {}
	public Usuario(long cPF, String nome, int debito, String senha) {
		super();
		CPF = cPF;
		this.nome = nome;
		this.debito = debito;
		this.senha = senha;
	}
	@Id
	@Column(name="CPF")
	public long getCPF() {
		return CPF;
	}

	public void setCPF(long cPF) {
		CPF = cPF;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getDebito() {
		return debito;
	}

	public void setDebito(int debito) {
		this.debito = debito;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
