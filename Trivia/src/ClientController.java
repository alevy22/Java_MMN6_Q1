import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClientController {

	@FXML
	private VBox vBox;

	@FXML
	private HBox hBox;

	private ClientThread thread;
	
    @FXML
    private Button actionBtn;
    
    private boolean isActionStart;
	
    public void initialize() {
    	isActionStart = true;
    	actionBtn.setText("Start");
    }

	@FXML
	void onActionButtonPressed(ActionEvent event) {
		if (isActionStart) {
			System.out.println("start");
			thread = new ClientThread(this, "localhost");
			thread.start();
			System.out.println(thread.getQuestion());
		} else {
			System.out.println("stop");
		}
		
		this.isActionStart = !isActionStart;
		actionBtn.setText(isActionStart ? "Start" : "Stop");
	}

}
