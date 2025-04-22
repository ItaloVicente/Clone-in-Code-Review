  public void testJsonObjectWithCompression() {
    tc = new SerializingTranscoder(CachedData.MAX_SIZE, false);
    String json = "{\"aaaaaaaaaaaaaaaaaaaaaaaaa\":"
            + "\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
    tc.setCompressionThreshold(8);
    CachedData cd = tc.encode(json);
    assertEquals(SerializingTranscoder.COMPRESSED, cd.getFlags());
    assertFalse(Arrays.equals(json.getBytes(), cd.getData()));
    assertEquals(json, tc.decode(cd));
  }

