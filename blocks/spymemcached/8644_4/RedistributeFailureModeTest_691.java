  public void testMixedSetsAndUpdates() throws Exception {
    Collection<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
    Collection<String> keys = new ArrayList<String>();
    Thread.sleep(100);
    for (int i = 0; i < 100; i++) {
      String key = "k" + i;
      futures.add(client.set(key, 10, key));
      futures.add(client.add(key, 10, "a" + i));
      keys.add(key);
    }
    Map<String, Object> m = client.getBulk(keys);
    assertEquals(100, m.size());
    for (Map.Entry<String, Object> me : m.entrySet()) {
      assertEquals(me.getKey(), me.getValue());
    }
    for (Iterator<Future<Boolean>> i = futures.iterator(); i.hasNext();) {
      assertTrue(i.next().get(10, TimeUnit.MILLISECONDS));
      assertFalse(i.next().get(10, TimeUnit.MILLISECONDS));
    }
    System.err.println(getName() + " complete.");
  }
