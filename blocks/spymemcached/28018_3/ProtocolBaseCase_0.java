
    final String key2 = "castestkey2";

    assertTrue(client.add(key2, 0, "value").get());
    CASValue<Object> casValue = client.gets(key2);

    assertEquals(CASResponse.OK,
      client.cas(key2, casValue.getCas(), 3, "new val"));

    Thread.sleep(5000);
    assertNull(client.get(key2));
