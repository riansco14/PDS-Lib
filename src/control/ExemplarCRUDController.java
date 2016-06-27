package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import DAO.LivroDAO;
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
import negocio.BibliotecaNegocio;
import util.Dialogs;

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
		String quantidade=Dialogs.showInput("Insira a quantidade a ser somada", "Insira a quantidade a ser somada", "");

			int tmp = 0;
			try {
				tmp=Integer.parseInt(quantidade);
				String isbn=tabela.getSelectionModel().getSelectedItem().getIsbn();
				if(negocio.adicionarLivroEstoque(isbn, tmp))
						 Dialogs.showInformation("Transação realizada com sucesso", "Informação", "Quantidade alterada com sucesso");
				else 
						 Dialogs.showError("Houve algum problema na Transação", "Error", "A quantidade não foi alterada");
				
			} catch (Exception e) {
				Dialogs.showError("Error", "Valor Invalido", "");
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
