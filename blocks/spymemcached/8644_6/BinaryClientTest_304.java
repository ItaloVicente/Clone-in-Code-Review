  public void testCASPrependSuccess() throws Exception {
    final String key = "append.key";
    assertTrue(client.set(key, 5, "test").get());
    CASValue<Object> casv = client.gets(key);
    assertTrue(client.prepend(casv.getCas(), key, "es").get());
    assertEquals("estest", client.get(key));
  }
