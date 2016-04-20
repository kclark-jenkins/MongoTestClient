package org.krisbox.utils.impl;

public class MongoCmdProperties {
	// String username, String password, String hostname, String port, String database
	private String  username;
	private String  password;
	private String  hostname;
	private Integer port;
	private String  database;
	private String  command;
	
	public MongoCmdProperties() {
		
	}
	
	public MongoCmdProperties(String username, String password, String hostname,
						 	  Integer port, String database, String command) {
		this.username = username;
		this.password = password;
		this.hostname = hostname;
		this.port     = port;
		this.database = database;
		this.command  = command;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getHostname() {
		return hostname;
	}
	
	public Integer getPort() {
		return port;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public String getCommand() {
		return command;
	}
}
