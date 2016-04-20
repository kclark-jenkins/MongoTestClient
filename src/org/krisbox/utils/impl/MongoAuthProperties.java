package org.krisbox.utils.impl;

public class MongoAuthProperties {
	
	/*
	 * 		System.setProperty("java.security.krb5.conf","C:/Users/clarkk/eclipse-workspace/MongoDB/krb5.conf");
		System.setProperty("java.security.krb5.realm","TESTLAB.COM");
		System.setProperty("java.security.krb5.kdc","testlab.com");
		System.setProperty("javax.security.auth.useSubjectCredsOnly","false");
		System.setProperty("java.security.auth.login.config","C:/Users/clarkk/eclipse-workspace/MongoDB/gss-jaas.conf");
	 */
	
	private String  krb5conf;
	private String  krb5realm;
	private String  krb5kdc;
	private String  authLoginConfig;
	private Boolean useSubjectCredsOnly;
	
	public MongoAuthProperties(String krb5conf, String krb5realm, String krb5kdc, String authLoginConfig, boolean useSubjectCredsOnly) {
		this.krb5conf            = krb5conf;
		this.krb5realm           = krb5realm;
		this.krb5kdc             = krb5kdc;
		this.authLoginConfig     = authLoginConfig;
		this.useSubjectCredsOnly = useSubjectCredsOnly;
	}
	
	public void setPropertiesNow() {
		System.setProperty("java.security.krb5.conf", krb5conf);
		System.setProperty("java.security.krb5.realm", krb5realm);
		System.setProperty("java.security.krb5.kdc", krb5kdc);
		System.setProperty("java.security.auth.login.config", authLoginConfig);
		System.setProperty("javax.security.auth.useSubjectCredsOnly", useSubjectCredsOnly.toString());
	}
	
	public void setKrb5conf(String krb5conf) {
		this.krb5conf = krb5conf;
	}
	
	public void setKrb5Realm(String krb5realm) {
		this.krb5realm = krb5realm;
	}
	
	public void setKrb5kdc(String krb5kdc) {
		this.krb5kdc = krb5kdc;
	}
	
	public void setAuthLoginConfig(String authLoginConfig) {
		this.authLoginConfig = authLoginConfig;
	}
	
	public String getKrb5Conf() {
		return krb5conf;
	}
	
	public String getKrb5realm() {
		return krb5realm;
	}
	
	public String getKrb5kdc() {
		return krb5kdc;
	}
	
	public String getAuthLoginConfig() {
		return authLoginConfig;
	}
	
	public Boolean useSubjectCredsOnly() {
		return useSubjectCredsOnly;
	}
}
