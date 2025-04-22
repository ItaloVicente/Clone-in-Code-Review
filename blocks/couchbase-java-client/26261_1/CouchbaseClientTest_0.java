    assertTrue("Key add was not persisted to master : "
      + addOp.getStatus().getMessage(), addOp.get());

    if(client.getAvailableServers().size() < 2) {
      return;
    }
