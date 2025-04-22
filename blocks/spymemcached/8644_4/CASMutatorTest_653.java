  public void testCASUpdateWithNullInitialNoExistingVal() throws Throwable {
    assertNull(client.get("x"));
    Long rv = mutator.cas("x", (Long) null, 0, mutation);
    assertNull(rv);
    assertNull(client.get("x"));
  }
