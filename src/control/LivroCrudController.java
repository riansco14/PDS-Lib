package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import DAO.LivroDAO;
import VO.BibliotecaNegocio;
import control.ExemplarCRUDController.LivroPropriety;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Categoria;
import model.Editora;
import model.Livro;

public class LivroCrudController implements Initializable {

	@FXML
	TableView<LivroPropriety> tabela;

	@FXML
	TableColumn<LivroPropriety, String> colISBN;
	@FXML
	TableColumn<LivroPropriety, String> colTitulo;
	@FXML
	TableColumn<LivroPropriety, String> colAutor;
	@FXML
	TableColumn<LivroPropriety, String> colEditora;
	@FXML
	TableColumn<LivroPropriety, String> colCategoria;
	@FXML
	TextField fieldSearch;

	ObservableList<LivroPropriety> listItens = FXCollections
			.observableArrayList();
	BibliotecaNegocio negocio = new BibliotecaNegocio();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colISBN.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
				"isbn"));
		colTitulo
				.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
						"titulo"));
		colAutor.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
				"autor"));
		colCategoria
				.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
						"categoria"));
		colEditora
				.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
						"editora"));

			
		
		tabela.setItems(listItens);

	}
	@FXML
	public void actionDeletar(ActionEvent event) {
		Alert alert=new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Deseja Excluir esse Livro ?");
		Optional<ButtonType> tmp=alert.showAndWait();
		if(tmp.get()==ButtonType.OK){
			String isbn=tabela.getSelectionModel().getSelectedItem().getIsbn();
			if(negocio.excluirLivro(isbn)){
				alert=new Alert(AlertType.INFORMATION);
				alert.setContentText("Livro Excluido");
				alert.showAndWait();
			}
		}
	
		
	}
	
	@FXML
	public void actionSearch(ActionEvent event) {
		List<Livro> livros = null; 
		if(fieldSearch.getText().length()==0){
			livros = negocio.selecionarTodosLivros();
		}
		else{
			listItens.clear();
			LivroDAO dao=new LivroDAO();
			livros=dao.localizarTitle(fieldSearch.getText());
		}
		
		if (livros != null && livros.size() != 0) {

			for (Livro l : livros) {

				listItens.add(new LivroPropriety(l));
			}
		}
		tabela.setItems(listItens);
		
		
	}



	public class LivroPropriety {
		private StringProperty isbn;
		private StringProperty titulo;
		private StringProperty autor;
		private StringProperty categoria;
		private StringProperty editora;

		public LivroPropriety(Livro livro) {
			this.isbn = new SimpleStringProperty(livro.getIsbn());
			this.titulo = new SimpleStringProperty(livro.getTitulo());
			this.autor = new SimpleStringProperty(livro.getAutor());
			this.categoria = new SimpleStringProperty(livro.getCategoria()
					.getDescricao());
			this.editora = new SimpleStringProperty(livro.getEditora()
					.getNome());
		}

		public String getIsbn() {
			return isbn.get();
		}

		public void setIsbn(String isbn) {
			this.isbn.set(isbn);
		}

		public String getTitulo() {
			return titulo.get();
		}

		public void setTitulo(String titulo) {
			this.titulo.set(titulo);
		}

		public String getAutor() {
			return autor.get();
		}

		public void setAutor(String autor) {
			this.autor.set(autor);
		}

		public String getCategoria() {
			return categoria.get();
		}

		public void setCategoria(String categoria) {
			this.categoria.set(categoria);
		}

		public String getEditora() {
			return editora.get();
		}

		public void setEditora(String editora) {
			this.editora.set(editora);
		}

	}

}
