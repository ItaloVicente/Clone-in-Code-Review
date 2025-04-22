  @Test
  public void testNumericStrings() {
    Query query = new Query();
    query.setKey("300");
    assertEquals("?key=300", query.toString());

    query.setKey("0000");
    assertEquals("?key=0000", query.toString());

    query.setKey("[300,400,\"500\"]");
    assertEquals("?key=[300,400,\"500\"]", query.toString());

    query.setKey(ComplexKey.of("300"));
    assertEquals("?key=\"300\"", query.toString());

    query.setKey(ComplexKey.of(300));
    assertEquals("?key=300", query.toString());

    query.setKey(ComplexKey.of(300, 400, "500"));
    assertEquals("?key=[300,400,\"500\"]", query.toString());

    query = new Query();
    query.setRangeStart(ComplexKey.of("0000"));
    query.setRangeEnd(ComplexKey.of("Level+2"));
    assertEquals("?startkey=\"0000\"&endkey=\"Level+2\"", query.toString());
  }

