package grupo.uno.quickiz.designPatterns;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class QuestionLogger {
	
	private static QuestionLogger questionLogger = new QuestionLogger();
	
	private final String questionsLogFile = "Registro_Preguntas_Propuestas.log";
	private PrintWriter writer;
	
	private QuestionLogger() {
		try {
			FileWriter fileWriter = new FileWriter(questionsLogFile);
            writer = new PrintWriter(fileWriter, true);
		} catch(IOException e) {}

	}
	
	public static QuestionLogger getInstance() {
		return questionLogger; 
	}
	
	public void logQuestion(String title, String code, String variables ,String pool, Timestamp date) {
		writer.println("Pregunta: "+title+" | Código: "+code+" | Variables: "+variables+" | Pozo: "+pool+" | Fecha: "+date);
	}
	
	

}
