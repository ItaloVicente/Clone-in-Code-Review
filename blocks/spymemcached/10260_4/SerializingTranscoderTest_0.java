  public void testJsonObject() {
    String json = "{\"aaaaaaaaaaaaaaaaaaaaaaaaa\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
    tc.setCompressionThreshold(8);
    CachedData cd = tc.encode(json);
    assertTrue(Arrays.equals(json.getBytes(), cd.getData()));
    assertNotSame(SerializingTranscoder.COMPRESSED, cd.getFlags());
    assertEquals(json, tc.decode(cd));
  }

