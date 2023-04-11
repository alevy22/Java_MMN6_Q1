import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
			sendQuestions();
		} catch (Exception e) {
			System.err.println("Error handling client: " + e.getMessage());
		}
	}
//	
//	private void sendQuestions() throws Exception {
//	    OutputStream outputStream = clientSocket.getOutputStream();
//	    ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
//	    Set<Integer> usedQuestionIndexes = new HashSet<>();
//	    
//	    while (usedQuestionIndexes.size() < questions.size()) {
//	        // generate a random question index that hasn't been used before
//	        int randomIndex;
//	        do {
//	            randomIndex = (int) (Math.random() * questions.size());
//	        } while (usedQuestionIndexes.contains(randomIndex));
//	        usedQuestionIndexes.add(randomIndex);
//	        
//	        // send the random question to the client
//	        System.out.println(questions.get(randomIndex));
//	        objOutputStream.writeObject(questions.get(randomIndex));
////	        objOutputStream.flush();
//	        
//	        // wait for 10 seconds
//	        Thread.sleep(10000);
//	    }
//	    
//	    // close the streams and socket
//	    objOutputStream.close();
//	    outputStream.close();
//	    clientSocket.close();
//	}

	private void sendQuestions() throws Exception {
		OutputStream outputStream = clientSocket.getOutputStream();
		ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
		
		// send question to the client
		objOutputStream.writeObject(questions.get(0));
				
		objOutputStream.close();
		outputStream.close();
		clientSocket.close();
	}
}
