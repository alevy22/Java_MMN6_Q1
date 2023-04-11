import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
	private static final int PORT = 3333;
	private static final String QUESTIONS_FILE = "Questions.txt";
	private List<Question> questions;

	public static void main(String[] args) {
		Server server = new Server();
		server.run();
	}

	public void run() {
		loadQuestions();
				
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server listening on port " + PORT);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				 new ServerThread(clientSocket, questions).start();
			}

		} catch (IOException e) {
			System.err.println("Error starting server: " + e.getMessage());
		}
	}

	private void loadQuestions() {
		questions = new ArrayList<>();
		File file = new File(QUESTIONS_FILE);

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String questionText = scanner.nextLine();
				String correctAnswer = scanner.nextLine();
				String[] answers = new String[4];

				// Choose a random index for the correct answer between 0 and 3
				int correctIndex = (int) (Math.random() * 4);

				// Fill the answers array with correct and wrong answers
				for (int i = 0; i < 4; i++) {
					if (i == correctIndex) {
						answers[i] = correctAnswer;
					} else {
						answers[i] = scanner.nextLine();
					}
				}

				Question question = new Question(questionText, answers, correctIndex);
				questions.add(question);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error loading questions: " + e.getMessage());
		}
	}
}
