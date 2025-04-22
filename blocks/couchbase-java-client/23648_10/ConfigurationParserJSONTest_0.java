  public void testInvalidURI() throws ParseException{
    try {
      configParser.parseBase(INVALID_BASE_STRING);
    } catch (ConnectionException e) {
      assertEquals(e.getMessage(), "Connection URI is either incorrect " +
        "or invalid as it cannot be parsed.");
    }
  }

