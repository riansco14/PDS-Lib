package control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import control.LivroCrudController.LivroPropriety;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Livro;
import negocio.BibliotecaNegocio;
import util.Dialogs;
import util.ValidationField;

public class EmprestimoUpdateController implements Initializable {
	@FXML	private TableColumn<LivroPropriety, String> colTitulo;

	@FXML	private TableView<LivroPropriety> tabela;

	@FXML	private TableColumn<LivroPropriety, String> colISBN;

	@FXML	private TextField fieldCPF;
	
	ObservableList<LivroPropriety> listItens = FXCollections
			.observableArrayList();
	BibliotecaNegocio negocio = new BibliotecaNegocio();
	
	
	@FXML
	void actionSearch(ActionEvent event) {
		if(ValidationField.isEmptyAllFields(fieldCPF)) return;
		
		listItens.clear();
		long tmp=Long.parseLong(fieldCPF.getText());
		List<Livro> list=negocio.localizarLivro(tmp);
		
		if(list!=null || list.size()>0){
			for (Livro livro : list) {
				listItens.add(new LivroPropriety(livro));
			}
		}
		tabela.setItems(listItens);
	}
	
	@FXML
	void actionDevolver(ActionEvent event) throws Exception {
		if (negocio.devolverLivro(tabela.getSelectionModel().getSelectedItem().getIsbn(), Long.parseLong(fieldCPF.getText()))) 				
			 Dialogs.showInformation("Transação realizada com sucesso", "Informação", "Devolucao realizada com sucesso!");
		else 
			 Dialogs.showError("Houve algum problema na Transação", "Error", "O livro não foi devolvido");

			
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colISBN.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
				"isbn"));
		colTitulo
				.setCellValueFactory(new PropertyValueFactory<LivroPropriety, String>(
						"titulo"));
		
		
		tabela.setItems(listItens);
		
	}
	
	public class LivroPropriety {
		private StringProperty isbn;
		private StringProperty titulo;

		public LivroPropriety(Livro livro) {
			this.isbn = new SimpleStringProperty(livro.getIsbn());
			this.titulo = new SimpleStringProperty(livro.getTitulo());
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

	}
}
