    final int items = 1000;
    List<String> keysList = new ArrayList<String>(items);
    for (int i = 0; i < items; i++) {
      assertTrue(client.set("getBulkWithCallback" + i, 0, "content").get());
      keysList.add("getBulkWithCallback" + i);
    }

