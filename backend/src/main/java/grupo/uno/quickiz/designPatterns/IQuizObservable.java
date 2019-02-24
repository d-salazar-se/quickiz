package grupo.uno.quickiz.designPatterns;

import java.sql.Timestamp;

public interface IQuizObservable {
	
	public void setChange(String studentName, Timestamp startAt);

}
