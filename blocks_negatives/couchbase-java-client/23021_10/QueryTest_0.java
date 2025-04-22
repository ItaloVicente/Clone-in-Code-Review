  /**
   * Tests the "debug" argument.
   */
  @Test
  public void testDebug() {
    Query query = new Query();
    query.setDebug(true);

    assertEquals(1, query.getArgs().size());
    assertEquals("?debug=true", query.toString());
  }

