package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import DAO.LivroDAO;
import DAO.UsuarioDAO;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Livro;
import model.Usuario;
import negocio.BibliotecaNegocio;
import util.Dialogs;
import control.LivroCrudController.LivroPropriety;

public class UserCRUDController implements Initializable {
	@FXML	TableView<UsuarioPropriety> tabela;

	@FXML TableColumn<UsuarioPropriety,SimpleLongProperty > colCPF;
	@FXML TableColumn<UsuarioPropriety, String> colNome;
	@FXML 	TextField fieldSearch;

	ObservableList<UsuarioPropriety> listItens = FXCollections
			.observableArrayList();
	BibliotecaNegocio negocio = new BibliotecaNegocio();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colCPF.setCellValueFactory(new PropertyValueFactory<UsuarioPropriety, SimpleLongProperty>(
				"CPF"));
		colNome
				.setCellValueFactory(new PropertyValueFactory<UsuarioPropriety, String>(
						"nome"));

	}
	@FXML
	public void actionDeletar(ActionEvent event) {
		
		if(Dialogs.showConfirmation("Confirmação", "Escolha uma opção", "Deseja Excluir esse Usuario Permanentemente ?")){
			long cpf=tabela.getSelectionModel().getSelectedItem().getCPF();
			
			if(negocio.excluirUsuario(cpf))
				Dialogs.showInformation("Transação realizada com sucesso", "Informação", "Usuario Excluido com sucesso");
			else
				Dialogs.showError("Houve algum problema na Transação", "Error", "Usuario não foi excluido");
		
		}
	}

	
	@FXML
	public void actionSearch(ActionEvent event) {
		listItens.clear();
		List<Usuario> usuario = new ArrayList<>(); 
		if(fieldSearch.getText().length()==0){
			usuario = negocio.listarUsuarios();
		}
		else{
			UsuarioDAO dao=new UsuarioDAO();
			Usuario tmp=dao.localizar(fieldSearch.getText());
			if(tmp!=null)
				usuario.add(tmp);
		}
		
		if (usuario != null && usuario.size() != 0) {

			for (Usuario l : usuario) {

				listItens.add(new UsuarioPropriety(l));
			}
		}
		tabela.setItems(listItens);
		
		
	}
	
	@FXML
	public void actionModificar(ActionEvent event) {
		if(tabela.getSelectionModel().getSelectedItem()!=null){
			String nome=tabela.getSelectionModel().getSelectedItem().getNome();
			long CPF=tabela.getSelectionModel().getSelectedItem().getCPF();
			

			   String senha=Dialogs.showInput("Modificar Senha", "Modificar Senha de "+nome, "Insira sua nova Senha:");
			   String senha2=Dialogs.showInput("Modificar Senha", "Modificar Senha de "+nome, "Insira sua nova Senha:");
				 
			   if(senha.equals(senha2)){
					   if(new UsuarioDAO().update(CPF,senha))
						   Dialogs.showInformation("Transação realizada com sucesso", "Informação", "Sua Senha foi Alterada com sucesso");
					   else 
						   Dialogs.showError("Houve algum problema na Transação", "Error", "Sua Senha não foi alterada");
			   }
			   }
		
	}
	
	public class UsuarioPropriety {
		private StringProperty nome;
		private SimpleLongProperty CPF;
		

		public UsuarioPropriety(Usuario usuario) {
			this.nome = new SimpleStringProperty(usuario.getNome());
			this.CPF = new SimpleLongProperty(usuario.getIdUsuario());
			
		
		}

		public String getNome() {
			return nome.get();
		}

		public void setNome(String isbn) {
			this.nome.set(isbn);
		}

		public long getCPF() {
			return (long) CPF.get();
		}

		public void setCPF(long titulo) {
			this.CPF.set(titulo);
		}
		

	}
	
	
}
