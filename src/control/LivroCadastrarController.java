package control;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.UsuarioDAO;
import model.Categoria;
import model.Editora;
import model.Livro;
import negocio.BibliotecaNegocio;
import util.Dialogs;
import util.ValidationField;
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
		boolean fieldIsEmpty=ValidationField.isEmptyAllFields(fieldISBN,fieldAutor,fieldCategoria,fieldEditora,fieldTitle);
		if(fieldIsEmpty) return;
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
		
		
		 if(new BibliotecaNegocio().inserirLivro(livro, editora))
			   Dialogs.showInformation("Transa��o realizada com sucesso", "Informa��o", "Livro inserido com sucesso");
		   else 
			   Dialogs.showError("Houve algum problema na Transa��o", "Error", "Seu Livro n�o foi inserido");
		
	}

}
