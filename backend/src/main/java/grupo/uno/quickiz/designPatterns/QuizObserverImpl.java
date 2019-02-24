package grupo.uno.quickiz.designPatterns;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class QuizObserverImpl implements IQuizObserver{
	
	private final String questionsLogFile = "Registro_Quizzes_Rendidos.log";
	private PrintWriter writer;
	
	public QuizObserverImpl() {
		try {
			FileWriter fileWriter = new FileWriter(questionsLogFile);
            writer = new PrintWriter(fileWriter, true);
		} catch(IOException e) {}
	}
	
	@Override
	public void update(String studentName, Timestamp startAt) {
		writer.println("Alumno: "+studentName+" | Fecha/Hora Inicio: "+startAt);
	}

}
