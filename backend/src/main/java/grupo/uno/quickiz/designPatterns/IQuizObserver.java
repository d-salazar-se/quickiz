package grupo.uno.quickiz.designPatterns;

import java.sql.Timestamp;

public interface IQuizObserver {
	
	public void update(String studentName, Timestamp startAt);

}
