package control;

import java.net.URL;
import java.util.ResourceBundle;

import VO.BibliotecaNegocio;
import model.Categoria;
import model.Editora;
import model.Livro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class LivroCadastrarController implements Initializable{
	@FXML TextField fieldISBN;
	@FXML TextField fieldTitle;
	@FXML TextField fieldAutor;
	@FXML TextField fieldEditora;
	@FXML TextField fieldCategoria;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML
	private void buttonInserir(ActionEvent event){
		Livro     livro     = new Livro();
		Categoria categoria = new Categoria();
		Editora   editora   = new Editora();
		
		editora.setNome(fieldEditora.getText());
		categoria.setDescricao(fieldCategoria.getText());
		
		livro.setIsbn(fieldISBN.getText());
		livro.setAutor(fieldAutor.getText());
		livro.setTitulo(fieldTitle.getText());
		livro.setEditora(editora);
		livro.setCategoria(categoria);
		
		new BibliotecaNegocio().inserirLivro(livro, editora);
		
	}

}
