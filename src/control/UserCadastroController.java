package control;

import java.net.URL;
import java.util.ResourceBundle;

import VO.BibliotecaNegocio;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Usuario;

public class UserCadastroController implements Initializable{
	@FXML TextField fieldNome;
	@FXML TextField fieldCPF;
	@FXML TextField fieldSenha;
	@FXML TextField fieldSenha2;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fieldCPF.addEventFilter(KeyEvent.ANY,new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
			
			try {
				Integer.parseInt(event.getCharacter());
				
			} catch (NumberFormatException e) {
				event.consume();
			}
			}
		});
	}
	
	@FXML
	public void actionCadastrar(ActionEvent event) {
		BibliotecaNegocio negocio=new BibliotecaNegocio();
		if(fieldSenha.getText().equals(fieldSenha2.getText())){
			Usuario usuario=new Usuario();
			usuario.setIdUsuario(Long.parseLong(fieldCPF.getText()));
			usuario.setNome(fieldNome.getText());
			usuario.setSenha(fieldSenha.getText());
			
			if(negocio.cadastrarUsuario(usuario))
				System.out.println("cadastrad");
		}
		
		
	}
}
