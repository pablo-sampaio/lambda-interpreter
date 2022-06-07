package lambdac;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class ErrorReport {
	private ArrayList<String> errorMessages;
	
	public ErrorReport() {
		errorMessages = new ArrayList<String>();
	}
	
	public void addMessage(String msg) {
		errorMessages.add(msg);
	}
	
	public String getMessage(int i) {
		return errorMessages.get(i);
	}

	public int getNumMessages() {
		return errorMessages.size();
	}
	
	public Collection<String> getAllMessages() {
		return Collections.unmodifiableCollection(errorMessages);
	}

}
