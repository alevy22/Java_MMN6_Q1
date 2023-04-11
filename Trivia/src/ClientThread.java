import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import javafx.application.Platform;

public class ClientThread extends Thread {

	private ClientController cont;
	private String ip;
	private Question question;
	private int score;
	private Socket socket;

	public ClientThread(ClientController cont, String ip) {
		this.cont = cont;
		this.ip = ip;
		this.question = null;
		this.setScore(0);
	}

	public void run() {
		super.run();
		try {
			handleQuestions();
		} catch (Exception e) { 
			e.printStackTrace(); 
		}	
	}

	public void handleQuestions() throws Exception {
		socket = new Socket(ip, 3333);
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objInputStream = new ObjectInputStream(inputStream);

		Question receivedQuestion = (Question) objInputStream.readObject();
		System.out.println("Received question from server: " + receivedQuestion);

		new Thread(() -> {
			Platform.runLater(() -> {
				// update the UI on the FX Application Thread
				cont.updateQuestion(receivedQuestion);
			});
		}).start();

		objInputStream.close();
		inputStream.close();
		socket.close();
	}

	public Question getQuestion() {
		return question;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
