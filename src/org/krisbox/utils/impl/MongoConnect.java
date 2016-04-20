package org.krisbox.utils.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoConnect {
	private MongoClient mongoClient = null;
	private MongoCmdProperties mdbCmdProps = null;
	private MongoAuthenticationTypes mdbAuthType = null;
	private CommandResult resultSet = null;
	
	public MongoConnect() {
		
	}
	
	public MongoConnect(MongoAuthenticationTypes auth, MongoCmdProperties mdbProps) throws UnsupportedEncodingException {
		createMongoClient(auth, mdbProps);
	}
	
	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	public void setMdbCmdProps(MongoCmdProperties mdbCmdProps) {
		this.mdbCmdProps = mdbCmdProps;
	}
	
	public void setMdbAuthTypes(MongoAuthenticationTypes mdbAuthType) {
		this.mdbAuthType = mdbAuthType;
	}
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}
	
	public MongoCmdProperties getMdbCmdProps() {
		return mdbCmdProps;
	}
	
	public MongoAuthenticationTypes getMdbAuthTypes() {
		return mdbAuthType;
	}
	
	public <TDocument> Object getResultSet(ResultSetTypes resultType, Class<TDocument> documentClass, CodecRegistry codecRegistry) {
		Object convertedResultSet = null;
		
		switch(resultType) {
		case BSON:
			convertedResultSet = resultSet.toBsonDocument(documentClass, codecRegistry);
			break;
		case JSON:
			convertedResultSet = resultSet.toJson();
			break;
		case MAP:
			convertedResultSet = resultSet.toMap();
			break;
		case STRING:
			convertedResultSet = resultSet.toString();
			break;
		default:
			convertedResultSet = null;
		}
		return convertedResultSet;
	}
	
	public CommandResult executeCommand(String command) {
		CommandResult resultSet = null;
		
		if(mongoClient == null) {
			createMongoClient(mdbAuthType, mdbCmdProps);
			resultSet = ((DB) mongoClient.getDatabase(mdbCmdProps.getDatabase())).command(command);
			this.resultSet = resultSet;
		}else{
			resultSet = ((DB) mongoClient.getDatabase(mdbCmdProps.getDatabase())).command(command);
			this.resultSet = resultSet;
		}
		
		return resultSet;
	}
	
	private MongoClient createMongoClient(MongoAuthenticationTypes auth, MongoCmdProperties mdbCmdProps) {
		MongoClient mongoClient = null;
		
		switch(auth) {
		case SCRAM_SHA1:
			break;
		case MONGODB_CR:
			break;
		case X509:
			break;
		case LDAP_PROXY:
			break;
		case KERBEROS:
			if(mdbCmdProps.getPassword().isEmpty() || mdbCmdProps.getPassword() == null) {
				List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
				ServerAddress address = new ServerAddress(mdbCmdProps.getHostname(), mdbCmdProps.getPort());
				serverAddresses.add(address);
				List<MongoCredential> credentials = new ArrayList<MongoCredential>();
				MongoCredential credential = MongoCredential.createGSSAPICredential(mdbCmdProps.getUsername());
				credentials.add(credential);
				
				return new MongoClient(serverAddresses, credentials);
			}
			break;
		default:
		}
		
		return mongoClient;
	}
}
