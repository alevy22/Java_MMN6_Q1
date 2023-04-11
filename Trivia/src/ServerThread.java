import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

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
			handleClient();
		} catch (IOException e) {
			System.err.println("Error handling client: " + e.getMessage());
		}
	}

	private void handleClient() throws IOException {
		try (Scanner scanner = new Scanner(clientSocket.getInputStream())) {
			System.out.println("New client connected from " + clientSocket.getPort());

			List<Question> unusedQuestions = new ArrayList<>(questions);
			Collections.shuffle(unusedQuestions);
			List<Integer> askedQuestions = new ArrayList<>();

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					if (!unusedQuestions.isEmpty()) {
						Question question = unusedQuestions.remove(0);
						askedQuestions.add(question.hashCode());
						try {
							System.out.println("question to send to client: " + question.toString());
							sendQuestion(question, clientSocket);
						} catch (Exception e) {
							System.err.println("Error sending question: " + e.getMessage());
							timer.cancel();
						}
					} else {
						timer.cancel();
					}
				}
			}, 0, 20000);
		}
	}

	private void sendQuestion(Question question, Socket clientSocket) throws Exception {
		OutputStream outputStream = clientSocket.getOutputStream();
		ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
		
		InputStream inputStream = clientSocket.getInputStream();
		ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
		
		objOutputStream.writeObject(question);
		System.out.println("Server sent question to client: " + clientSocket.getInetAddress());
		
		objOutputStream.close();
		outputStream.close();
		inputStream.close();
		objInputStream.close();
		clientSocket.close();
	}
}
