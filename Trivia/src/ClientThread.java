import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

	private ClientController cont;
	private String ip;
	private Question question;
	private int score;

	public ClientThread(ClientController cont, String ip) {
		this.cont = cont;
		this.ip = ip;
		this.question = null;
		this.setScore(0);
	}

	public void run() {
		super.run();
		try {
			handleQuestion();
		} catch (Exception e) { 
			e.printStackTrace(); 
		}	
	}
	
	public void close() {
		
	}

	public void handleQuestion() throws Exception {
		try (Socket s = new Socket(ip, 3333)) {
			OutputStream outputStream = s.getOutputStream();
			ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);

			InputStream inputStream = s.getInputStream();
			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);

			// get question from server
			Question question;
			question = (Question)objInputStream.readObject();
			this.question = question;
			// TO DO 
			// present in the UI 
			System.out.println(question.toString());

			// close all connections              	
//			outputStream.close();
//			objOutputStream.close();
//			inputStream.close();
//			objInputStream.close();
//			s.close();
		}
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
