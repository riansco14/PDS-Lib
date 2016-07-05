package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Usuario;
import negocio.BibliotecaNegocio;
import util.Dialogs;
import util.ValidationField;

public class UserCadastroController implements Initializable{
	@FXML TextField fieldNome;
	@FXML TextField fieldCPF;
	@FXML TextField fieldEmail;
	@FXML TextField fieldSenha;
	@FXML TextField fieldSenha2;
	BibliotecaNegocio negocio=new BibliotecaNegocio();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

        fieldCPF.addEventFilter(KeyEvent.ANY, ValidationField.getDigitEvent());
	}
	
	@FXML
	public void actionCadastrar(ActionEvent event) {
		boolean fieldIsEmpty=ValidationField.isEmptyAllFields(fieldCPF,fieldNome,fieldSenha,fieldSenha2,fieldEmail);
		if(fieldIsEmpty) return;
		
		if(fieldSenha.getText().equals(fieldSenha2.getText())){
			Usuario usuario=new Usuario();
			usuario.setIdUsuario(Long.parseLong(fieldCPF.getText()));
			usuario.setNome(fieldNome.getText());
			usuario.setSenha(fieldSenha.getText());
			usuario.setEmail(fieldEmail.getText());
			
			if(negocio.cadastrarUsuario(usuario))
				Dialogs.showInformation("Transação realizada com sucesso", "Informação", "Usuario inserido com sucesso");
			else
				Dialogs.showError("Houve algum problema na Transação", "Error", "Usuario não foi inserido");
		
		}
		
		
	}
}
