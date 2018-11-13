package aibot.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;


public class InquiryRest extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger("AUTO-RESPONSE");
	
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//read json param and build string
		request.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        
        //convert string to JSON object
        System.out.println(sb);
        JSONObject jsonArray;
		jsonArray = new JSONObject(sb.toString());
		JSONObject variablesJsonObj = (JSONObject) jsonArray.get("variables");
        Object senderAddress = this.getVariableByKey(variablesJsonObj, "senderAddress");
        Object recipientAddress = this.getVariableByKey(variablesJsonObj, "recipientAddress");
        Object mailText = this.getVariableByKey(variablesJsonObj, "mailText");
        
        //start process
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        PrintWriter out = response.getWriter();
        
        ProcessInstance processInstance;
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("senderAddress", senderAddress);
	        map.put("recipientAddress", recipientAddress);
	        map.put("mailText", mailText);
	        processInstance = runtimeService.startProcessInstanceByMessage("IncomingMessage", map);
	        
        String processInstanceId = processInstance.getId();
	}
	
	private String getVariableByKey(JSONObject variableObj, String key) {
		JSONObject tempJsonObj = (JSONObject) variableObj.get(key);
        return (String) tempJsonObj.get("value");
	}
	
}