  public void testAddKeyExists() throws Exception {
    assertNull(client.get("test1"));
    assert !client.asyncGet("test1").getStatus().isSuccess();
    assertTrue(client.set("test1", 5, "test1value").get());
    assertEquals("test1value", client.get("test1"));
    assertFalse(client.add("test1", 5, "ignoredvalue").get());
    assert !client.add("test1", 5, "ignoredvalue").getStatus().isSuccess();
    assertEquals("test1value", client.get("test1"));
  }

  public void testReplaceKeyNoExist() throws Exception {
    OperationFuture<Boolean> replace = client.replace("t1", 5, "replacevalue");
    OperationStatus status = replace.getStatus();
    assert !status.isSuccess();
    assertEquals("Not found", status.getMessage());
  }

  public void testSetInvalidKeyBlank(){
    try {
      client.set(EMPTY,5,EMPTY);
    } catch (IllegalArgumentException e) {
      assertEquals("Key must contain at least one character.", e.getMessage());
    }
  }

  public void testGetValBlank() throws Exception {
    assertTrue(client.set("blankValue",0,EMPTY).get());
    assertEquals(EMPTY, client.get("blankValue"));
  }

