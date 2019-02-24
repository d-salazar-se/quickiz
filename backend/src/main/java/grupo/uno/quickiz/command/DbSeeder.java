package grupo.uno.quickiz.command;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import grupo.uno.quickiz.models.Pool;
import grupo.uno.quickiz.models.Question;
import grupo.uno.quickiz.models.QuestionInstance;
import grupo.uno.quickiz.models.QuestionStructure;
import grupo.uno.quickiz.models.Quiz;
import grupo.uno.quickiz.models.QuizStructure;
import grupo.uno.quickiz.models.QuizSchedule;
import grupo.uno.quickiz.repositories.PoolRepository;
import grupo.uno.quickiz.repositories.QuestionInstanceRepository;
import grupo.uno.quickiz.repositories.QuestionRepository;
import grupo.uno.quickiz.repositories.QuestionStructureRepository;
import grupo.uno.quickiz.repositories.QuizRepository;
import grupo.uno.quickiz.repositories.QuizStructureRepository;
import grupo.uno.quickiz.repositories.QuizScheduleRepository;

@Component
public class DbSeeder implements CommandLineRunner{
	
	private QuizStructureRepository quizStructureRepository;
	private QuestionStructureRepository questionStructureRepository;
	private PoolRepository poolRepository;
	private QuizRepository quizRepository;
	private QuestionRepository questionRepository;
	private QuestionInstanceRepository questionInstanceRepository;
	private QuizScheduleRepository quizScheduleRepository;
	
	public DbSeeder(QuizStructureRepository quizStructureRepository, QuestionStructureRepository questionStructureRepository, PoolRepository poolRepository, QuizRepository quizRepository, QuestionRepository questionRepository, QuestionInstanceRepository questionInstanceRepository, QuizScheduleRepository quizScheduleRepository) {
		this.quizStructureRepository = quizStructureRepository;
		this.questionStructureRepository = questionStructureRepository;
		this.poolRepository = poolRepository;
		this.quizRepository = quizRepository;
		this.questionRepository = questionRepository;
		this.questionInstanceRepository= questionInstanceRepository;
		this.quizScheduleRepository= quizScheduleRepository;
	}
	
	private static final String QUESTION_1 = "¿Cual es el resultado de la siguiente operación?";

	@Override
	public void run(String...strings) throws Exception {
		this.quizRepository.deleteAll();
		this.quizStructureRepository.deleteAll();
		this.questionStructureRepository.deleteAll();
		this.poolRepository.deleteAll();
		this.questionRepository.deleteAll();
		this.questionInstanceRepository.deleteAll();
		this.quizScheduleRepository.deleteAll();
		
		HttpRequest httpRequestPythonCode = new HttpRequest();
		
		Pool pool1 = new Pool("Enteros, reales y operadores aritméticos.");
		Pool pool2 = new Pool("Booleanos, operadores lógicos y cadenas.");
		Pool pool3 = new Pool("Listas");
		Pool pool4 = new Pool("Tuplas.");
		Pool pool5 = new Pool("Diccionarios.");
		Pool pool6 = new Pool("Operadores relacionales.");
		Pool pool7 = new Pool("Sentencias condicionales.");
		Pool pool8 = new Pool("Bucles.");
		Pool pool9 = new Pool("Funciones.");
		Pool pool10 = new Pool("Entrada y salida de archivos.");

		List<Pool> pools = Arrays.asList(pool1,pool2,pool3,pool4,pool5,pool6,pool7,pool8,pool9,pool10);
		
		this.poolRepository.saveAll(pools);
		
		QuizStructure quizStructure1 = new QuizStructure("Quiz#1", 15, 3);
		
		List<QuizStructure> quizStructures = Arrays.asList(quizStructure1);
		
		this.quizStructureRepository.saveAll(quizStructures);
		
		QuestionStructure questionStructure1 = new QuestionStructure(6,pool1,quizStructure1);
		
		List<QuestionStructure> questionStructures = Arrays.asList(questionStructure1);
		quizStructure1.setQuestionsStructures(questionStructures);
		this.questionStructureRepository.saveAll(questionStructures);
		
		Question question1 = new Question(6, QUESTION_1, "print(a+b)","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool1);
		Question question2 = new Question(6, QUESTION_1, "print(a-b)","a=[1,2,3,4,5]\nb=[2,4,6,8,10]", pool1);
		Question question3 = new Question(2, QUESTION_1, "print(a*b)","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool3);
		Question question4 = new Question(2, QUESTION_1, "print(a/b)","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool3);
		Question question5 = new Question(2, QUESTION_1, "print(a+(2*b))","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool3);
		Question question6 = new Question(2, QUESTION_1, "print((5*a)-b)","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool3);
		Question question7 = new Question(2, QUESTION_1, "print((a+b)/b)","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool3);
		Question question8 = new Question(2, QUESTION_1, "print((2*a*b)+(a*b))","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool3);
		Question question9 = new Question(3, QUESTION_1, "print((2*a)+b)","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool1);
		Question question10 = new Question(3, QUESTION_1, "print(5*(a/b))","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool1);
		Question question11 = new Question(3, QUESTION_1, "print(7*(a-b))","a=[1,2,3,4,5,6,7,8,9,10]\nb=[1,2,3,4,5,6,7,8,9,10]", pool1);
		
		List<Question> questions = Arrays.asList(question1, question2, question3, question4, question5,question6,question7,question8,question9,question10,question11);
		
		this.questionRepository.saveAll(questions);
		
		// Quiz quiz = new Quiz(new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),quizStructure1);
		
		// System.out.println(quizStructure1.getQuestionsStructures());
		
		// for(QuestionStructure questionStructure : quizStructure1.getQuestionsStructures()) {
		// 	Question question = questionRepository.findQuestion(questionStructure.getScore(), questionStructure.getPool().getId());
		// 	String[] variables = question.getVariables().split("\n");
		// 	String json = "";
		// 	String variablesQuestionInstance = "{";
		// 	for(int i = 0;i < variables.length; i++) {
		// 		String[] variableX = variables[i].split("=");
		// 		String[] valoresVariablex = variableX[1].split(",");
		// 		String chosenValue = valoresVariablex[new Random().nextInt(valoresVariablex.length)];
		// 		chosenValue = chosenValue.replaceAll("[\\[\\]]", "");
		// 		variablesQuestionInstance = variablesQuestionInstance + "\""+variableX[0]+"\"" + ":" + chosenValue + ",";
		// 		json = json + variableX[0] + "=" + chosenValue + "\n";
				
		// 		//System.out.println(variableX[0]+"--->"+variableX[1]);
	
		// 	}
		// 	json += question.getCode();
		// 	variablesQuestionInstance = variablesQuestionInstance.substring(0, (variablesQuestionInstance.length()-1));
		// 	variablesQuestionInstance = variablesQuestionInstance + "}";
		// 	String pythonAnswer = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json+"\""+"}]}");
		// 	System.out.println(json);
		// 	System.out.println(pythonAnswer);
		// 	System.out.println("HERE-------->>>>"+variablesQuestionInstance);
		// 	System.out.println();
		// 	//System.out.println(question.getCode()+"---->"+question.getScore());
		// 	//System.out.println();
			
		// }
		
		// this.quizRepository.save(quiz);
				
		/*
		Question question1 = new Question(2, QUESTION_1, "a+b","a=(1,50);b=(1,50)", pool1);
		Question question2 = new Question(2, QUESTION_1, "a-b","a=(1,50);b=(1,50)", pool1);
		Question question3 = new Question(2, QUESTION_1, "a*b","a=(1,50);b=(1,50)", pool1);
		Question question4 = new Question(3, "Mostrar con un for los números de \"a\" hasta \"b\".", "","a=(1,50);b=(1,1000)", pool8);
		Question question5 = new Question(3, "Mostrar con un while los números de \"a\" hasta \"b\".", "","a=(1,50);b=(1,1000)", pool8);
		Question question6 = new Question(2, QUESTION_1, "a/b","a=(1,50);b=(1,50)", pool1);

		
		
		Quiz quiz1 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure2);
		Quiz quiz2 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure2);
		Quiz quiz3 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure2);
		Quiz quiz4 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure2);
		Quiz quiz5 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure2);
		Quiz quiz6 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure1);
		Quiz quiz7 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure1);
		Quiz quiz8 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure1);
		Quiz quiz9 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure1);
		Quiz quiz10 = new Quiz(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quizStructure1);

		QuizSchedule quizSchedule1 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz1);
		QuizSchedule quizSchedule2 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz2);
		QuizSchedule quizSchedule3 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz3);
		QuizSchedule quizSchedule4 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz4);
		QuizSchedule quizSchedule5 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz5);
		QuizSchedule quizSchedule6 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz6);
		QuizSchedule quizSchedule7 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz7);
		QuizSchedule quizSchedule8 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz8);
		QuizSchedule quizSchedule9 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz9);
		QuizSchedule quizSchedule10 = new QuizSchedule(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), quiz10);
		
		
		
		
		
		
		List<Question> questions = Arrays.asList(question1, question2, question3, question4, question5);
		List<QuestionInstance> questionInstances = new ArrayList<>();
		List<QuizSchedule> quizSchedules = Arrays.asList(quizSchedule1,quizSchedule2,quizSchedule3,quizSchedule4,quizSchedule5,quizSchedule6,quizSchedule7,quizSchedule8,quizSchedule9,quizSchedule10);
		List<Quiz> quizzes = Arrays.asList(quiz1,quiz2,quiz3,quiz4,quiz5,quiz6,quiz7,quiz8,quiz9,quiz10);
		List<Question> questions2 = Arrays.asList(question1, question2, question6);
		
		// for (Question temp : questions) {
		// 	QuestionInstance questionInstance = new QuestionInstance();
			
		// 	Random rand = new Random();
		// 	String jsonCode= temp.getCode();
		// 	String[] jsonVar = temp.getVariables().split(";"); //Ej: ["a=(1,2)", "b=(3,4)"]
			
		// 	String[] jsonA = jsonVar[0].split("="); //Ej: ["a", "(1,2)"]
		// 	String[] jsonAValue =  jsonA[1].replaceAll("[()]", "").split(",");
		// 	System.out.println("Rango de A = "+jsonA[1]);
			
		// 	String[] jsonB = jsonVar[1].split("="); //Ej: ["b", "(3,4)"]
		// 	String[] jsonBValue =  jsonB[1].replaceAll("[()]", "").split(",");
		// 	System.out.println("Rango de B = "+jsonB[1]);
			
			
		// 	String aValue = Integer.toString(rand.nextInt(Integer.parseInt(jsonAValue[1]))+Integer.parseInt(jsonAValue[0]));
		// 	String bValue = Integer.toString(rand.nextInt(Integer.parseInt(jsonBValue[1]))+Integer.parseInt(jsonBValue[0]));
			
		// 	System.out.println();
			
		// 	String json = jsonA[0]+"="+aValue+"\n"+jsonB[0]+"="+bValue+"\nprint("+jsonCode+")";
			
		// 	String pythonAnswer = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json+"\""+"}]}");
			
		// 	questionInstance.setAnswer(pythonAnswer);
		// 	questionInstance.setVariableValues("a="+aValue+";b="+bValue);
		// 	questionInstance.setQuestion(temp);
		// 	questionInstance.setQuiz(quiz1);
			
		// 	questionInstances.add(questionInstance);





		// 	QuestionInstance questionInstance2 = new QuestionInstance();
			
		// 	Random rand2 = new Random();
		// 	String jsonCode2= temp.getCode();
		// 	String[] jsonVar2 = temp.getVariables().split(";"); //Ej: ["a=(1,2)", "b=(3,4)"]
			
		// 	String[] jsonA2 = jsonVar2[0].split("="); //Ej: ["a", "(1,2)"]
		// 	String[] jsonAValue2 =  jsonA2[1].replaceAll("[()]", "").split(",");
			
		// 	String[] jsonB2 = jsonVar2[1].split("="); //Ej: ["b", "(3,4)"]
		// 	String[] jsonBValue2 =  jsonB2[1].replaceAll("[()]", "").split(",");
			
		// 	String aValue2 = Integer.toString(rand2.nextInt(Integer.parseInt(jsonAValue2[1]))+Integer.parseInt(jsonAValue2[0]));
		// 	String bValue2 = Integer.toString(rand2.nextInt(Integer.parseInt(jsonBValue2[1]))+Integer.parseInt(jsonBValue2[0]));
			
			
		// 	String json2 = jsonA2[0]+"="+aValue2+"\n"+jsonB2[0]+"="+bValue2+"\nprint("+jsonCode2+")";
			
		// 	String pythonAnswer2 = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json2+"\""+"}]}");
			
		// 	questionInstance2.setAnswer(pythonAnswer2);
		// 	questionInstance2.setVariableValues("a="+aValue2+";b="+bValue2);
		// 	questionInstance2.setQuestion(temp);
		// 	questionInstance2.setQuiz(quiz2);
			
		// 	questionInstances.add(questionInstance2);

			QuestionInstance questionInstance3 = new QuestionInstance();
			
		// 	Random rand3 = new Random();
		// 	String jsonCode3= temp.getCode();
		// 	String[] jsonVar3 = temp.getVariables().split(";"); //Ej: ["a=(1,3)", "b=(3,4)"]
			
		// 	String[] jsonA3 = jsonVar3[0].split("="); //Ej: ["a", "(1,3)"]
		// 	String[] jsonAValue3 =  jsonA3[1].replaceAll("[()]", "").split(",");
			
		// 	String[] jsonB3 = jsonVar3[1].split("="); //Ej: ["b", "(3,4)"]
		// 	String[] jsonBValue3 =  jsonB3[1].replaceAll("[()]", "").split(",");
			
		// 	String aValue3 = Integer.toString(rand3.nextInt(Integer.parseInt(jsonAValue3[1]))+Integer.parseInt(jsonAValue3[0]));
		// 	String bValue3 = Integer.toString(rand3.nextInt(Integer.parseInt(jsonBValue3[1]))+Integer.parseInt(jsonBValue3[0]));
			
			
		// 	String json3 = jsonA3[0]+"="+aValue3+"\n"+jsonB3[0]+"="+bValue3+"\nprint("+jsonCode3+")";
			
		// 	String pythonAnswer3 = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json3+"\""+"}]}");
			
		// 	questionInstance3.setAnswer(pythonAnswer3);
		// 	questionInstance3.setVariableValues("a="+aValue3+";b="+bValue3);
		// 	questionInstance3.setQuestion(temp);
		// 	questionInstance3.setQuiz(quiz3);
			
		// 	questionInstances.add(questionInstance3);

			QuestionInstance questionInstance4 = new QuestionInstance();
			
		// 	Random rand4 = new Random();
		// 	String jsonCode4= temp.getCode();
		// 	String[] jsonVar4 = temp.getVariables().split(";"); //Ej: ["a=(1,4)", "b=(4,4)"]
			
		// 	String[] jsonA4 = jsonVar4[0].split("="); //Ej: ["a", "(1,4)"]
		// 	String[] jsonAValue4 =  jsonA4[1].replaceAll("[()]", "").split(",");
			
		// 	String[] jsonB4 = jsonVar4[1].split("="); //Ej: ["b", "(4,4)"]
		// 	String[] jsonBValue4 =  jsonB4[1].replaceAll("[()]", "").split(",");
			
		// 	String aValue4 = Integer.toString(rand4.nextInt(Integer.parseInt(jsonAValue4[1]))+Integer.parseInt(jsonAValue4[0]));
		// 	String bValue4 = Integer.toString(rand4.nextInt(Integer.parseInt(jsonBValue4[1]))+Integer.parseInt(jsonBValue4[0]));
			
			String json4 = jsonA4[0]+"="+aValue4+"\n"+jsonB4[0]+"="+bValue4+"\nprint("+jsonCode4+")";
			
		// 	String pythonAnswer4 = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json4+"\""+"}]}");
			
		// 	questionInstance4.setAnswer(pythonAnswer4);
		// 	questionInstance4.setVariableValues("a="+aValue4+";b="+bValue4);
		// 	questionInstance4.setQuestion(temp);
		// 	questionInstance4.setQuiz(quiz4);
			
		// 	questionInstances.add(questionInstance4);

			QuestionInstance questionInstance5 = new QuestionInstance();
			
		// 	Random rand5 = new Random();
		// 	String jsonCode5= temp.getCode();
		// 	String[] jsonVar5 = temp.getVariables().split(";"); //Ej: ["a=(1,5)", "b=(5,5)"]
			
		// 	String[] jsonA5 = jsonVar5[0].split("="); //Ej: ["a", "(1,5)"]
		// 	String[] jsonAValue5 =  jsonA5[1].replaceAll("[()]", "").split(",");
			
		// 	String[] jsonB5 = jsonVar5[1].split("="); //Ej: ["b", "(5,5)"]
		// 	String[] jsonBValue5 =  jsonB5[1].replaceAll("[()]", "").split(",");
			
		// 	String aValue5 = Integer.toString(rand5.nextInt(Integer.parseInt(jsonAValue5[1]))+Integer.parseInt(jsonAValue5[0]));
		// 	String bValue5 = Integer.toString(rand5.nextInt(Integer.parseInt(jsonBValue5[1]))+Integer.parseInt(jsonBValue5[0]));
			
			
		// 	String json5 = jsonA5[0]+"="+aValue5+"\n"+jsonB5[0]+"="+bValue5+"\nprint("+jsonCode5+")";
			
		// 	String pythonAnswer5 = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json5+"\""+"}]}");
			
		// 	questionInstance5.setAnswer(pythonAnswer5);
		// 	questionInstance5.setVariableValues("a="+aValue5+";b="+bValue5);
		// 	questionInstance5.setQuestion(temp);
		// 	questionInstance5.setQuiz(quiz5);
			
		// 	questionInstances.add(questionInstance5);
		// }

		// for (Question temp : questions2) {

		// 	QuestionInstance questionInstance2 = new QuestionInstance();
			
		// 	Random rand2 = new Random();
		// 	String jsonCode2= temp.getCode();
		// 	String[] jsonVar2 = temp.getVariables().split(";"); //Ej: ["a=(1,2)", "b=(3,4)"]
			
		// 	String[] jsonA2 = jsonVar2[0].split("="); //Ej: ["a", "(1,2)"]
		// 	String[] jsonAValue2 =  jsonA2[1].replaceAll("[()]", "").split(",");
			
		// 	String[] jsonB2 = jsonVar2[1].split("="); //Ej: ["b", "(3,4)"]
		// 	String[] jsonBValue2 =  jsonB2[1].replaceAll("[()]", "").split(",");
			
		// 	String aValue2 = Integer.toString(rand2.nextInt(Integer.parseInt(jsonAValue2[1]))+Integer.parseInt(jsonAValue2[0]));
		// 	String bValue2 = Integer.toString(rand2.nextInt(Integer.parseInt(jsonBValue2[1]))+Integer.parseInt(jsonBValue2[0]));
			
			
		// 	String json2 = jsonA2[0]+"="+aValue2+"\n"+jsonB2[0]+"="+bValue2+"\nprint("+jsonCode2+")";
			
		// 	String pythonAnswer2 = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json2+"\""+"}]}");
			
		// 	questionInstance2.setAnswer(pythonAnswer2);
		// 	questionInstance2.setVariableValues("a="+aValue2+";b="+bValue2);
		// 	questionInstance2.setQuestion(temp);
		// 	questionInstance2.setQuiz(quiz6);
			
		// 	questionInstances.add(questionInstance2);



		// 	QuestionInstance questionInstance3 = new QuestionInstance();
			
		// 	Random rand3 = new Random();
		// 	String jsonCode3= temp.getCode();
		// 	String[] jsonVar3 = temp.getVariables().split(";"); //Ej: ["a=(1,3)", "b=(3,4)"]
			
		// 	String[] jsonA3 = jsonVar3[0].split("="); //Ej: ["a", "(1,3)"]
		// 	String[] jsonAValue3 =  jsonA3[1].replaceAll("[()]", "").split(",");
			
		// 	String[] jsonB3 = jsonVar3[1].split("="); //Ej: ["b", "(3,4)"]
		// 	String[] jsonBValue3 =  jsonB3[1].replaceAll("[()]", "").split(",");
			
		// 	String aValue3 = Integer.toString(rand3.nextInt(Integer.parseInt(jsonAValue3[1]))+Integer.parseInt(jsonAValue3[0]));
		// 	String bValue3 = Integer.toString(rand3.nextInt(Integer.parseInt(jsonBValue3[1]))+Integer.parseInt(jsonBValue3[0]));
			
			
		// 	String json3 = jsonA3[0]+"="+aValue3+"\n"+jsonB3[0]+"="+bValue3+"\nprint("+jsonCode3+")";
			
		// 	String pythonAnswer3 = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json3+"\""+"}]}");
			
		// 	questionInstance3.setAnswer(pythonAnswer3);
		// 	questionInstance3.setVariableValues("a="+aValue3+";b="+bValue3);
		// 	questionInstance3.setQuestion(temp);
		// 	questionInstance3.setQuiz(quiz7);
			
		// 	questionInstances.add(questionInstance3);


		// 	QuestionInstance questionInstance4 = new QuestionInstance();
			
		// 	Random rand4 = new Random();
		// 	String jsonCode4= temp.getCode();
		// 	String[] jsonVar4 = temp.getVariables().split(";"); //Ej: ["a=(1,4)", "b=(4,4)"]
			
		// 	String[] jsonA4 = jsonVar4[0].split("="); //Ej: ["a", "(1,4)"]
		// 	String[] jsonAValue4 =  jsonA4[1].replaceAll("[()]", "").split(",");
			
		// 	String[] jsonB4 = jsonVar4[1].split("="); //Ej: ["b", "(4,4)"]
		// 	String[] jsonBValue4 =  jsonB4[1].replaceAll("[()]", "").split(",");
			
		// 	String aValue4 = Integer.toString(rand4.nextInt(Integer.parseInt(jsonAValue4[1]))+Integer.parseInt(jsonAValue4[0]));
		// 	String bValue4 = Integer.toString(rand4.nextInt(Integer.parseInt(jsonBValue4[1]))+Integer.parseInt(jsonBValue4[0]));
			
			
		// 	String json4 = jsonA4[0]+"="+aValue4+"\n"+jsonB4[0]+"="+bValue4+"\nprint("+jsonCode4+")";
			
		// 	String pythonAnswer4 = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json4+"\""+"}]}");
			
		// 	questionInstance4.setAnswer(pythonAnswer4);
		// 	questionInstance4.setVariableValues("a="+aValue4+";b="+bValue4);
		// 	questionInstance4.setQuestion(temp);
		// 	questionInstance4.setQuiz(quiz8);
			
		// 	questionInstances.add(questionInstance4);

		// 	QuestionInstance questionInstance5 = new QuestionInstance();
			
		// 	Random rand5 = new Random();
		// 	String jsonCode5= temp.getCode();
		// 	String[] jsonVar5 = temp.getVariables().split(";"); //Ej: ["a=(1,5)", "b=(5,5)"]
			
		// 	String[] jsonA5 = jsonVar5[0].split("="); //Ej: ["a", "(1,5)"]
		// 	String[] jsonAValue5 =  jsonA5[1].replaceAll("[()]", "").split(",");
			
		// 	String[] jsonB5 = jsonVar5[1].split("="); //Ej: ["b", "(5,5)"]
		// 	String[] jsonBValue5 =  jsonB5[1].replaceAll("[()]", "").split(",");
			
		// 	String aValue5 = Integer.toString(rand5.nextInt(Integer.parseInt(jsonAValue5[1]))+Integer.parseInt(jsonAValue5[0]));
		// 	String bValue5 = Integer.toString(rand5.nextInt(Integer.parseInt(jsonBValue5[1]))+Integer.parseInt(jsonBValue5[0]));
			
			
		// 	String json5 = jsonA5[0]+"="+aValue5+"\n"+jsonB5[0]+"="+bValue5+"\nprint("+jsonCode5+")";
			
		// 	String pythonAnswer5 = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json5+"\""+"}]}");
			
		// 	questionInstance5.setAnswer(pythonAnswer5);
		// 	questionInstance5.setVariableValues("a="+aValue5+";b="+bValue5);
		// 	questionInstance5.setQuestion(temp);
		// 	questionInstance5.setQuiz(quiz9);
			
		// 	questionInstances.add(questionInstance5);

		// 	QuestionInstance questionInstance6 = new QuestionInstance();
			
		// 	Random rand6 = new Random();
		// 	String jsonCode6= temp.getCode();
		// 	String[] jsonVar6 = temp.getVariables().split(";"); //Ej: ["a=(1,6)", "b=(6,6)"]
			
		// 	String[] jsonA6 = jsonVar6[0].split("="); //Ej: ["a", "(1,6)"]
		// 	String[] jsonAValue6 =  jsonA6[1].replaceAll("[()]", "").split(",");
			
		// 	String[] jsonB6 = jsonVar6[1].split("="); //Ej: ["b", "(6,6)"]
		// 	String[] jsonBValue6 =  jsonB6[1].replaceAll("[()]", "").split(",");
			
		// 	String aValue6 = Integer.toString(rand6.nextInt(Integer.parseInt(jsonAValue6[1]))+Integer.parseInt(jsonAValue6[0]));
		// 	String bValue6 = Integer.toString(rand6.nextInt(Integer.parseInt(jsonBValue6[1]))+Integer.parseInt(jsonBValue6[0]));
			
			
		// 	String json6 = jsonA6[0]+"="+aValue6+"\n"+jsonB6[0]+"="+bValue6+"\nprint("+jsonCode6+")";
			
		// 	String pythonAnswer6 = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json6+"\""+"}]}");
			
		// 	questionInstance6.setAnswer(pythonAnswer6);
		// 	questionInstance6.setVariableValues("a="+aValue6+";b="+bValue6);
		// 	questionInstance6.setQuestion(temp);
		// 	questionInstance6.setQuiz(quiz10);
			
		// 	questionInstances.add(questionInstance6);
	
		// }		
		this.poolRepository.saveAll(pools);
		this.quizStructureRepository.saveAll(quizStructures);
		this.questionRepository.saveAll(questions);
		this.questionRepository.save(question6);
		this.quizRepository.saveAll(quizzes);
		this.questionInstanceRepository.saveAll(questionInstances);
		this.quizScheduleRepository.saveAll(quizSchedules);
		*/
	}

}
