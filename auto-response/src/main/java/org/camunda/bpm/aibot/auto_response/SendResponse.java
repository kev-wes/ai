package org.camunda.bpm.aibot.auto_response;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendResponse implements JavaDelegate{
	
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("The response \"" + execution.getVariable("responseText") + "\" has been sent to \"" 
	+ execution.getVariable("recipientAddress") + "\".");
	}

}
