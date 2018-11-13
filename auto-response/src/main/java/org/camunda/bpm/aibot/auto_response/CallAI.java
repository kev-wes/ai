package org.camunda.bpm.aibot.auto_response;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class CallAI implements JavaDelegate{

	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("recipientAddress", (String)execution.getVariable("recipientAddress"));
		execution.setVariable("senderAddress", (String)execution.getVariable("senderAddress"));
		execution.setVariable("responseText", (String)execution.getVariable("mailText"));
		System.out.println("A mail from " + (String)execution.getVariable("senderAddress") +
				" to " + (String)execution.getVariable("recipientAddress") +" has been received.");
		System.out.println("Text: " + (String)execution.getVariable("mailText")); 
		
		DB_connector connector = new DB_connector();
        // insert a new row for the incoming mail
		connector.insertInitialMail((String)execution.getVariable("mailText"),
				(String)execution.getVariable("senderAddress"),
				(String)execution.getVariable("recipientAddress"));
		// Set reference id of mail, this mail should be inserted
		// Into the subject of any following correspondence
		int id = connector.getMaxId();
		execution.setVariable("mailId", id);
		System.out.println("Mail ID: " + execution.getVariable("mailId").toString());
		// Call AI
		System.out.println("The AI has been called.");
	}

}
