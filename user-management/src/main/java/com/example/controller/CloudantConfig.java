package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix="cloudant")
@ConfigurationProperties()
@RefreshScope
public class CloudantConfig {

	@Value("${username}") private String username;
	@Value("${password}") private String password;
	@Value("${host}") 	  private String host;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  @Override
  public String toString() {
    return username + " : " + password + ":" + host;
  }


}
