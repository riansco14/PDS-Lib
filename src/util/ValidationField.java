package util;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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
	
	public static EventHandler<KeyEvent> getDigitEvent(){
		EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {

            private boolean willConsume = false;

            @Override
            public void handle(KeyEvent event) {

                if (willConsume) {
                    event.consume();
                }
                if (!(event.getCode().isArrowKey()||event.getCode().isDigitKey()||event.getCode().isFunctionKey())) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        willConsume = true;
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        willConsume = false;
                    }
                }
            }

        };
        return handler;
	}
	
}
