package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import DAO.LivroDAO;
import VO.BibliotecaNegocio;
import control.LivroCrudController.LivroPropriety;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Livro;

public class ExemplarCRUDController implements Initializable {
	@FXML
	TableView<LivroPropriety> tabela;
	
	@FXML
	TableColumn<LivroPropriety, String> colISBN;
	@FXML
	TableColumn<LivroPropriety, String> colTitulo;
	@FXML
	TableColumn<LivroPropriety, String> colQuantidade;
	
	ObservableList<LivroPropriety> listItens = FXCollections
			.observableArrayList();
	@FXML 
	TextField fieldSearch;
	
	BibliotecaNegocio negocio=new BibliotecaNegocio();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colISBN.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
				"isbn"));
		colTitulo
				.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
						"titulo"));
		colQuantidade
		.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
				"quantidade"));
		
		
		
		tabela.setItems(listItens);
	}
	
	@FXML
	public void actionSearch(ActionEvent event) {
		List<Livro> livros = new ArrayList<>(); 
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
				int quantidade =
					negocio.buscarQuantidade(l.getIsbn());

				listItens.add(new LivroPropriety(l, quantidade));
			}
		}
		tabela.setItems(listItens);
	}
	@FXML
	
	public void actionModificar(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Insira a quantidade a ser somada");
		dialog.setHeaderText("Insira a quantidade a ser somada");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			int tmp = 0;
			try {
				tmp=Integer.parseInt(result.get());
				String isbn=tabela.getSelectionModel().getSelectedItem().getIsbn();
				if(negocio.adicionarLivroEstoque(isbn, tmp)){
					Alert alert=new Alert(AlertType.INFORMATION);
					alert.setContentText("Quantidade alterada");
					alert.showAndWait();
				}
			} catch (Exception e) {
				Alert alert=new Alert(AlertType.ERROR);
				alert.setContentText("Valor Invalido");
				alert.showAndWait();
			}
			
		}
	}
	
	
	
	public class LivroPropriety {
		private StringProperty isbn;
		private StringProperty titulo;
		private IntegerProperty quantidade;
		
		public LivroPropriety(Livro livro,int quantidade) {
			this.isbn = new SimpleStringProperty(livro.getIsbn());
			this.titulo = new SimpleStringProperty(livro.getTitulo());
			this.quantidade= new SimpleIntegerProperty(quantidade);
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

		public int getQuantidade() {
			return quantidade.get();
		}

		public void setQuantidade(int quantidade) {
			this.quantidade.set(quantidade);
		}


	}

}
