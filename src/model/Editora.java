package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_editora")
public class Editora {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idEditora;
	
	private String nome;
	
	
	public Editora() { }
	
	public int getIdEditora() {
		return idEditora;
	}
	
	public void setIdEditora(int idEditora) {
		this.idEditora = idEditora;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
