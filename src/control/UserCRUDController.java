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
import VO.BibliotecaNegocio;
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
		Alert alert=new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Deseja Excluir esse Usuario Permanentemente ?");
		Optional<ButtonType> tmp=alert.showAndWait();
		if(tmp.get()==ButtonType.OK){
			long cpf=tabela.getSelectionModel().getSelectedItem().getCPF();
			if(negocio.excluirUsuario(cpf)){
				alert=new Alert(AlertType.INFORMATION);
				alert.setContentText("Usuario Excluido");
				alert.showAndWait();
			}
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
			
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Modificar Senha");
			dialog.setHeaderText("Modificar Senha de "+nome);
			dialog.setContentText("Insira sua nova Senha:");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
			  String senha=result.get();
			   result = dialog.showAndWait();
			   if (result.isPresent()){
				   String senha2=result.get();
				   if(senha.equals(senha2)){
					   new UsuarioDAO().update(CPF,senha);
					   new Alert(AlertType.INFORMATION,"Sua Senha foi Alterada com sucesso").showAndWait();
					  
				   }
			   }
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
