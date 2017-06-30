package org.jooby.kickstart;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppTest {
  private static final String localhost = "http://localhost:8080/";

  @ClassRule
  public static JoobyRule rule = new JoobyRule(new App());

  @Test
  public void getHelloWorld() throws UnirestException {
    final HttpResponse<String> response = Unirest.get(localhost).asString();

    assertNotNull(response);
    assertEquals("Hello World!", response.getBody());
  }
}