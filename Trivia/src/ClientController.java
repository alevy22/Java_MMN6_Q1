import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;

public class ClientController {

	@FXML
	private VBox mainVbox;

	@FXML
	private VBox questionContainer;

	@FXML
	private Label questionLabel;

	@FXML
	private RadioButton radio1;

	@FXML
	private RadioButton radio2;

	@FXML
	private RadioButton radio3;

	@FXML
	private RadioButton radio4;

	@FXML
	private VBox bottomVbox;

	@FXML
	private Label scoreLabel;

	@FXML
	private Label answerLabel;

	@FXML
	private Button submitBtn;

	@FXML
	private Button actionBtn;

	private Question currentQuestion;


	private boolean isActionStart;
	private ClientThread thread;
	private ToggleGroup toggleGroup;
	private int score;
	private int secondsRemaining = 10;
	private Timer timer;

	public void initialize() {
		isActionStart = true;
		score = 0;
		actionBtn.setText("Start");
		submitBtn.setVisible(false);
		answerLabel.setVisible(false);
		scoreLabel.setVisible(false);
		setTogglegroup();
		shouldShowQuestionContainer(false);
	}
	
	private void shouldShowQuestionContainer(boolean show) {
		questionContainer.setVisible(show);
	}

	private void setTogglegroup() {
		toggleGroup = new ToggleGroup();
		radio1.setToggleGroup(toggleGroup);
		radio2.setToggleGroup(toggleGroup);
		radio3.setToggleGroup(toggleGroup);
		radio4.setToggleGroup(toggleGroup);
	}



	public void updateQuestion(Question question) {
	    currentQuestion = question;

	    questionLabel.setText(currentQuestion.getQuestion());

	    radio1.setText(currentQuestion.getAnswers()[0]);
	    radio2.setText(currentQuestion.getAnswers()[1]);
	    radio3.setText(currentQuestion.getAnswers()[2]);
	    radio4.setText(currentQuestion.getAnswers()[3]);

	    submitBtn.setDisable(false);
		addClosingEvent();

	    // start a timer 
	    timer = new Timer();
	    timer.schedule(new TimerTask() {
	        public void run() {
	            secondsRemaining--;
	            System.out.println("Time left: " + secondsRemaining + " seconds");

	            // update score when the time ends
	            if (secondsRemaining == 0) {
	            	secondsRemaining = 10;
	            	
	            	RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
	        		int selectedIndex = toggleGroup.getToggles().indexOf(selectedRadioButton);
	        		boolean isUserCorrect = selectedIndex == currentQuestion.getCorrectAnswer();
	        		System.out.println(isUserCorrect);
	        		if (selectedIndex >= 0 && selectedIndex <= 3) {
	        			score = isUserCorrect ? score + 10 : score - 5;
	        			scoreLabel.setText("Score: " + score);
	        			answerLabel.setText(isUserCorrect ? "Correct Answer!" : "Wrong Answer!");
	        			answerLabel.setTextFill(isUserCorrect ?  Color.GREEN : Color.RED);
	        			answerLabel.setVisible(true);
	        			submitBtn.setDisable(true);
	        		} 
	            }
	        }
	    }, 0, 1000);
	}


	@FXML
	void onSubmitButtonPressed(ActionEvent event) {
		RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
		int selectedIndex = toggleGroup.getToggles().indexOf(selectedRadioButton);
		
		boolean isUserCorrect = selectedIndex == currentQuestion.getCorrectAnswer();
		
		if (selectedIndex >= 0 && selectedIndex <= 3) {
			score = isUserCorrect ? score + 10 : score - 5;
			scoreLabel.setText("Score: " + score);
			answerLabel.setText(isUserCorrect ? "Correct Answer!" : "Wrong Answer!");
			answerLabel.setTextFill(isUserCorrect ?  Color.GREEN : Color.RED);
			answerLabel.setVisible(true);
			submitBtn.setDisable(true);
		}		
	}

	@FXML
	void onActionButtonPressed(ActionEvent event) {
		if (isActionStart) {
			submitBtn.setVisible(true);
			scoreLabel.setVisible(true);
			shouldShowQuestionContainer(true);
			thread = new ClientThread(this, "localhost");
			thread.start();
		} else {
			shouldShowQuestionContainer(false);
			if (timer != null) {
				timer.cancel();
			}
		}

		this.isActionStart = !isActionStart;
		actionBtn.setText(isActionStart ? "Start Game" : "Stop game");
	}
	
	private void addClosingEvent() {
		Stage stage = (Stage)((Node) mainVbox).getScene().getWindow();
		stage.getScene().getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event1 -> {
			if (timer != null) {
				timer.cancel();
				secondsRemaining = 10;
			}
		});	
	}
}


