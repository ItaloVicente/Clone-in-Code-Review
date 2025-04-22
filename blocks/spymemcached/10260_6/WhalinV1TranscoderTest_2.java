  public void testJsonObject() {
    WhalinV1Transcoder transcoder = ((WhalinV1Transcoder)getTranscoder());
    String json = "{\"aaaaaaaaaaaaaaaaaaaaaaaaa\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
    transcoder.setCompressionThreshold(8);
    CachedData cd = transcoder.encode(json);
    assertFalse("Flags shows JSON was compressed", (cd.getFlags() & (1L << WhalinV1Transcoder.COMPRESSED)) != 0);
    assertTrue("JSON was incorrectly encoded", Arrays.equals(json.getBytes(),
        Arrays.copyOfRange(cd.getData(), 1, cd.getData().length)));
    assertEquals("JSON was harmed, should not have been", json, transcoder.decode(cd));
  }

