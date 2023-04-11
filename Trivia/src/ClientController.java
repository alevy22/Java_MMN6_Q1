import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ClientController {

    @FXML
    private VBox vBox;

    @FXML
    private Button startBtn;
    
    private ClientThread thread;

    @FXML
    void onStartButtonPressed(ActionEvent event) {
    	thread = new ClientThread(this, "localhost");
    	thread.start();
    	System.out.println(thread.getQuestion());
    }

}
