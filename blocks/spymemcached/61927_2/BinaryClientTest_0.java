  @Override
  public void testStupidlyLargeSetAndSizeOverride() throws Exception {
    Random r = new Random();
    SerializingTranscoder st = new SerializingTranscoder(Integer.MAX_VALUE);

    st.setCompressionThreshold(Integer.MAX_VALUE);

    byte[] data = new byte[21 * 1024 * 1024];
    r.nextBytes(data);

    OperationFuture<Boolean> setResult = client.set("bigassthing", 60, data, st);
    assertFalse(setResult.get());
    assertEquals(StatusCode.ERR_2BIG, setResult.getStatus().getStatusCode());

    client.set("k", 5, "Blah");
    assertEquals("Blah", client.get("k"));
  }

