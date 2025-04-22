  public void testTouch() throws Exception {
    assertNull(client.get("touchtest"));
    client.set("touchtest", 5, "touchtest");
    assertTrue(client.touch("touchtest", 2).get());
  }

