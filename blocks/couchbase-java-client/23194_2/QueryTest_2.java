  @Test
  public void testBooleans() {
    Query query = new Query();
    query.setKey(ComplexKey.of(true));
    query.setKeys(ComplexKey.of(false));

    assertEquals("?keys=false&key=true", query.toString());
  }

