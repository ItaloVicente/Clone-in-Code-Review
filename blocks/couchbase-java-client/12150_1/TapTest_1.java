    while (tc.hasMoreMessages()) {
      ResponseMessage m;
      if ((m = tc.getNextMessage()) != null) {
        String key = m.getKey() + "," + new String(m.getValue());
        if (items.containsKey(key)) {
          items.put(key, new Boolean(true));
        } else {
          fail();
