import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;


public class ServerThread extends Thread {
	private Socket clientSocket;
	private List<Question> questions;

	public ServerThread(Socket clientSocket, List<Question> questions) {
		this.clientSocket = clientSocket;
		this.questions = questions;
	}

	@Override
	public void run() {
		super.run();

		try {
			sendQuestion(questions.get(0));
		} catch (Exception e) {
			System.err.println("Error handling client: " + e.getMessage());
		}
	}

	private void sendQuestion(Question question) throws Exception {
		OutputStream outputStream = clientSocket.getOutputStream();
		ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
		
		InputStream inputStream = clientSocket.getInputStream();
		ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
		
		objOutputStream.writeObject(question);
		
		inputStream.close();
		objInputStream.close();
		objOutputStream.close();
		outputStream.close();
		clientSocket.close();
	}
}
