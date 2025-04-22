  @Test
  public void testBbox() throws UnsupportedEncodingException {
    Query query = new Query();
    query.setBbox(0, 1, 2.0, 3);

    assertEquals(1, query.getArgs().size());
    String result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?bbox=0.0,1.0,2.0,3.0", result);
  }

