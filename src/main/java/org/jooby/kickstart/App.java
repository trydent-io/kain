package org.jooby.kickstart;

import org.jooby.Jooby;

public class App extends Jooby {
  {
    get("/", () -> "Hello World!");
  }

  public static void main(String... args) {
    run(App.class, args);
  }
}
