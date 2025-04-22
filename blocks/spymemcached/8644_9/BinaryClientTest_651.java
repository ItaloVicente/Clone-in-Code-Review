  public void testCASPrependFail() throws Exception {
    final String key = "append.key";
    assertTrue(client.set(key, 5, "test").get());
    CASValue<Object> casv = client.gets(key);
    assertFalse(client.prepend(casv.getCas() + 1, key, "es").get());
    assertEquals("test", client.get(key));
  }
