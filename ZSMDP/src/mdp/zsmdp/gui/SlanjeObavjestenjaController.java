package mdp.zsmdp.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import mdp.zsmdp.threads.SlanjeObavjestenjaThread;

public class SlanjeObavjestenjaController {
	
	public Button posaljiButton;
	public TextArea obavjestenjeArea;
	
	public void poslajiClick() {
		String obavjestenje=obavjestenjeArea.getText();
		new SlanjeObavjestenjaThread(obavjestenje);
		posaljiButton.getScene().getWindow().hide();
	}

}
