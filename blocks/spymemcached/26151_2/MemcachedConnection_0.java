
    String verifyAlive = System.getProperty("net.spy.verify_alive_on_connect");
    if(verifyAlive != null && verifyAlive.equals("true")) {
      verifyAliveOnConnect = true;
    } else {
      verifyAliveOnConnect = false;
    }

    System.out.println("Verify on connect is: " + verifyAliveOnConnect);

