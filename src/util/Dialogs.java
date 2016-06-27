package util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class Dialogs {

		private static Alert alert;
		
		public static boolean showConfirmation(String titulo, String subTitulo,String mensagem)
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(titulo);
			alert.setHeaderText(subTitulo);
			alert.setContentText(mensagem);
			Optional<ButtonType> tmp=alert.showAndWait();
			if(tmp.get()==ButtonType.OK)
				return true;
			
			return false;
		}

		public static String showInput(String titulo, String subTitulo,String mensagem)
		{
			String tmp = null;
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle(titulo);
			dialog.setHeaderText(subTitulo);
			dialog.setContentText(mensagem);
			Optional<String> result; 
			do {
				result= dialog.showAndWait();
			} while (!result.isPresent());
			
			tmp=result.get();
			return tmp;
		}
		
		public static void showInformation(String titulo, String subTitulo,String mensagem)
		{
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(titulo);
			alert.setHeaderText(subTitulo);
			alert.setContentText(mensagem);
			alert.showAndWait();
		}

		public static void showError(String titulo, String subTitulo,String mensagem)
		{
			alert = new Alert(AlertType.ERROR);
			alert.setTitle(titulo);
			alert.setHeaderText(subTitulo);
			alert.setContentText(mensagem);
			alert.showAndWait();
		}
		
	
}
