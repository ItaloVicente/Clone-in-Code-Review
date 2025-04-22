      HashMap<String, Boolean> items = new HashMap<String, Boolean>();
      for (int i = 0; i < 25; i++) {
        client.set("key" + i, 0, "value" + i).get();
        items.put("key" + i + ",value" + i, new Boolean(false));
      }
      tc.tapDump(null);
