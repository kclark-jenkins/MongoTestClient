package org.krisbox.utils.impl;

import org.bson.codecs.configuration.CodecRegistry;
import org.krisbox.utils.MongoWrapper;

import com.mongodb.CommandResult;
import com.mongodb.MongoClient;

public class MongoContainer implements MongoWrapper {
	private MongoAuthProperties mdbAuthProperties;
	private MongoCmdProperties  mdbCmdProperties;
	private MongoConnect		mdbConnect;
	
	public MongoContainer() {
		
	}
	
	public MongoContainer(MongoAuthProperties mdbAuthProperties,
						  MongoCmdProperties mdbCmdProperties,
						  MongoConnect mdbConnect) {
		this.mdbAuthProperties  = mdbAuthProperties;
		this.mdbCmdProperties   = mdbCmdProperties;
		this.mdbConnect         = mdbConnect;
	}
	
	public void setMongoAuthProperties(MongoAuthProperties mdbProperties) {
		this.mdbAuthProperties = mdbProperties;
	}
	
	public void setMongoCmdProperties(MongoCmdProperties mdbCmdProperties) {
		this.mdbCmdProperties = mdbCmdProperties;
	}
	
	public void setMongoConnect(MongoConnect mdbConnect) {
		this.mdbConnect = mdbConnect;
	}
	
	public MongoAuthProperties getMongoAuthProperties() {
		return mdbAuthProperties;
	}
	
	public MongoCmdProperties getMongoCmdProperties() {
		return mdbCmdProperties;
	}
	
	public MongoConnect getMongoConnect() {
		return mdbConnect;
	}

	@Override
	public void setPropertiesNow() {
		mdbAuthProperties.setPropertiesNow();
	}

	@Override
	public void setKrb5conf(String krb5conf) {
		mdbAuthProperties.setKrb5conf(krb5conf);
	}

	@Override
	public void setKrb5Realm(String krb5realm) {
		mdbAuthProperties.setKrb5conf(krb5realm);
	}

	@Override
	public void setKrb5kdc(String krb5kdc) {
		mdbAuthProperties.setKrb5kdc(krb5kdc);
	}

	@Override
	public void setAuthLoginConfig(String authLoginConfig) {
		mdbAuthProperties.setAuthLoginConfig(authLoginConfig);
	}

	@Override
	public String getKrb5Conf() {
		return mdbAuthProperties.getKrb5Conf();
	}

	@Override
	public String getKrb5realm() {
		return mdbAuthProperties.getKrb5realm();
	}

	@Override
	public String getKrb5kdc() {
		return mdbAuthProperties.getKrb5kdc();
	}

	@Override
	public String getAuthLoginConfig() {
		return mdbAuthProperties.getAuthLoginConfig();
	}

	@Override
	public Boolean useSubjectCredsOnly() {
		return mdbAuthProperties.useSubjectCredsOnly();
	}

	@Override
	public void setUsername(String username) {
		mdbCmdProperties.setUsername(username);
	}

	@Override
	public void setPassword(String password) {
		mdbCmdProperties.setPassword(password);
	}

	@Override
	public void setHostname(String hostname) {
		mdbCmdProperties.setHostname(hostname);
	}

	@Override
	public void setPort(Integer port) {
		mdbCmdProperties.setPort(port);
	}

	@Override
	public void setDatabase(String database) {
		mdbCmdProperties.setDatabase(database);
	}

	@Override
	public void setCommand(String command) {
		mdbCmdProperties.setCommand(command);
	}

	@Override
	public String getUsername() {
		return mdbCmdProperties.getUsername();
	}

	@Override
	public String getPassword() {
		return mdbCmdProperties.getPassword();
	}

	@Override
	public String getHostname() {
		return mdbCmdProperties.getHostname();
	}

	@Override
	public Integer getPort() {
		return mdbCmdProperties.getPort();
	}

	@Override
	public String getDatabase() {
		return mdbCmdProperties.getDatabase();
	}

	@Override
	public String getCommand() {
		return mdbCmdProperties.getCommand();
	}

	@Override
	public void setMongoClient(MongoClient mongoClient) {
		mdbConnect.setMongoClient(mongoClient);
	}

	@Override
	public void setMdbCmdProps(MongoCmdProperties cmdProperties) {
	}

	@Override
	public void setMdbAuthTypes(MongoAuthenticationTypes authType) {
		mdbConnect.setMdbAuthTypes(authType);
	}

	@Override
	public MongoClient getMongoClient() {
		return mdbConnect.getMongoClient();
	}

	@Override
	public MongoCmdProperties getMdbCmdProps() {
		return mdbConnect.getMdbCmdProps();
	}

	@Override
	public MongoAuthenticationTypes getMdbAuthTypes() {
		return mdbConnect.getMdbAuthTypes();
	}

	@Override
	public <TDocument> Object getResultSet(ResultSetTypes resultType, Class<TDocument> documentClass,
			CodecRegistry codecRegistry) {
		return mdbConnect.getResultSet(resultType, documentClass, codecRegistry);
	}

	@Override
	public CommandResult executeCommand(String command) {
		return mdbConnect.executeCommand(command);
	}
}
