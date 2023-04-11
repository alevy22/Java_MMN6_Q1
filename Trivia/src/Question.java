import java.util.Arrays;
import java.util.Objects;

public class Question {
	private String question;
	private String[] answers = new String[4];
	private int correctAnswer; // index of the correct answer between 0-3
	
	public Question(String question, String[] answers, int correctAnswer) {
		super();
		this.question = question;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Question other = (Question) obj;
		return Arrays.equals(answers, other.answers) && correctAnswer == other.correctAnswer
				&& Objects.equals(question, other.question);
	}

	@Override
	public String toString() {
		return "[question = " + question + ", answers = " + Arrays.toString(answers) + ", correctAnswer = "
				+ correctAnswer + "]";
	}
}
