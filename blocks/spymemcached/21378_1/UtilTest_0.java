
  @Test
  public void testValidateKey() {
    boolean success = true;
    try {
      StringUtils.validateKey("legalKey");
    } catch(IllegalArgumentException ex) {
      success = false;
    }
    assertTrue(success);

    success = true;
    try {
      StringUtils.validateKey("key with spaces");
    } catch(IllegalArgumentException ex) {
      success = false;
    }
    assertTrue(success);

    success = true;
    try {
      StringUtils.validateKey("invalid\nkey");
    } catch(IllegalArgumentException ex) {
      success = false;
    }
    assertFalse(success);
  }
