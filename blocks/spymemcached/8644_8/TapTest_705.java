      long st = System.currentTimeMillis();
      while (tc.hasMoreMessages()) {
        if ((System.currentTimeMillis() - st) > TAP_DUMP_TIMEOUT) {
          fail("Tap dump took too long");
        }
        ResponseMessage m;
        if ((m = tc.getNextMessage()) != null) {
          String key = m.getKey() + "," + new String(m.getValue());
          if (items.containsKey(key)) {
            items.put(key, new Boolean(true));
          } else {
            fail();
          }
        }
      }
      checkTapKeys(items);
      assertTrue(client.flush().get().booleanValue());
    }
  }
