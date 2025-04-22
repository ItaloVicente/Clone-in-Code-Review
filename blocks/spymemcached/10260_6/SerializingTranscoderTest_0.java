  public void testJsonObject() {
    String json = "{\"aaaaaaaaaaaaaaaaaaaaaaaaa\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
    tc.setCompressionThreshold(8);
    CachedData cd = tc.encode(json);
    assertFalse("Flags shows JSON was compressed", (cd.getFlags() & (1L << SerializingTranscoder.COMPRESSED)) != 0);
    assertTrue("JSON was incorrectly encoded", Arrays.equals(json.getBytes(), cd.getData()));
    assertEquals("JSON was harmed, should not have been", json, tc.decode(cd));
  }

