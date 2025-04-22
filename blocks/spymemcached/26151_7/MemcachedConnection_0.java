
    String verifyAlive = System.getProperty("net.spy.verifyAliveOnConnect");
    if(verifyAlive != null && verifyAlive.equals("true")) {
      verifyAliveOnConnect = true;
    } else {
      verifyAliveOnConnect = false;
    }

