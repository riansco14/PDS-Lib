package util;

import javafx.scene.control.TextField;

public class ValidationField {
	public static boolean isEmptyAllFields(TextField...fields){
		for (TextField textField : fields) {
			if(textField.getText().length()==0){
				Dialogs.showInformation("Information", "Campo Vazio", textField.getPromptText());
				return true;
			}
		}
		return false;
	}
	
}
