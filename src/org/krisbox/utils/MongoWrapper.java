package org.krisbox.utils;

import org.bson.codecs.configuration.CodecRegistry;
import org.krisbox.utils.impl.MongoAuthenticationTypes;
import org.krisbox.utils.impl.MongoCmdProperties;
import org.krisbox.utils.impl.ResultSetTypes;

import com.mongodb.CommandResult;
import com.mongodb.MongoClient;

public interface MongoWrapper {
	public void setPropertiesNow();
	public void setKrb5conf(String krb5conf);
	public void setKrb5Realm(String krb5realm);
	public void setKrb5kdc(String krb5kdc);
	public void setAuthLoginConfig(String authLoginConfig);
	public String getKrb5Conf();
	public String getKrb5realm();
	public String getKrb5kdc();
	public String getAuthLoginConfig();
	public Boolean useSubjectCredsOnly();
	
	public void setUsername(String username);
	public void setPassword(String password);
	public void setHostname(String hostname);
	public void setPort(Integer port);
	public void setDatabase(String database);
	public void setCommand(String command);
	public String  getUsername();
	public String  getPassword();
	public String  getHostname();
	public Integer getPort();
	public String getDatabase();
	public String getCommand();
	
	public void 		 	  setMongoClient(MongoClient mongoClient);
	public void 		 	  setMdbCmdProps(MongoCmdProperties cmdProperties);
	public void 		 	  setMdbAuthTypes(MongoAuthenticationTypes authType);
	public MongoClient 		 	  getMongoClient();
	public MongoCmdProperties 		 	  getMdbCmdProps();
	public MongoAuthenticationTypes 		 	  getMdbAuthTypes();
	public <TDocument> Object getResultSet(ResultSetTypes resultType, Class<TDocument> documentClass, CodecRegistry codecRegistry);
	public CommandResult executeCommand(String command);
}
