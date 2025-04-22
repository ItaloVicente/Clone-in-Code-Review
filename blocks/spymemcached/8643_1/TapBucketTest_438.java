    for (Entry<String, Boolean> kv : items.entrySet()) {
      if (!kv.getValue().booleanValue()) {
        System.err.println("Error - Item: " + kv.getKey() + " was not"
            + " sent by the tap stream");
        failed = true;
      }
    }
    mc.flush();
    mc.shutdown();
    tc.shutdown();
