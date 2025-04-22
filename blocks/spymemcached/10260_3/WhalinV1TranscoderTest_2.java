  public void testJsonObject() {
    WhalinV1Transcoder transcoder = ((WhalinV1Transcoder)getTranscoder());
    String json = "{\"key\":\"value\"}";
    transcoder.setCompressionThreshold(8);
    CachedData cd = transcoder.encode(json);
    assertNotSame(SerializingTranscoder.COMPRESSED, cd.getFlags());
    assertTrue(Arrays.equals(json.getBytes(),
        Arrays.copyOfRange(cd.getData(), 1, cd.getData().length)));
    assertEquals(json, transcoder.decode(cd));
  }

