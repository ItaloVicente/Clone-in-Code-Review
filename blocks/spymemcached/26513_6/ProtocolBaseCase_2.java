  public void testTouch() throws Exception {
    assertNull(client.get("touchtest"));
    assertNull(client.get("nonexistent"));
    assertTrue(client.set("touchtest", 5, "touchtest").get());
    assertTrue(client.touch("touchtest", 2).get());
    assertFalse(client.touch("nonexistent", 2).get());
  }

