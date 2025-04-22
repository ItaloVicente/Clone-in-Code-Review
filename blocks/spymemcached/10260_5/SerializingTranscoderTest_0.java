  public void testJsonObject() {
    String json = "{\"aaaaaaaaaaaaaaaaaaaaaaaaa\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
    tc.setCompressionThreshold(8);
    CachedData cd = tc.encode(json);
    assertNotSame(SerializingTranscoder.COMPRESSED, cd.getFlags());
    assertTrue(Arrays.equals(json.getBytes(), cd.getData()));
    assertEquals(json, tc.decode(cd));
  }

