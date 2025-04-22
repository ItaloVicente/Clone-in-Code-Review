  private WhalinTranscoder tc;
  private TranscoderUtils tu;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    tc = new WhalinTranscoder();
    setTranscoder(tc);
    tu = new TranscoderUtils(false);
  }

  public void testNonserializable() throws Exception {
    try {
      tc.encode(new Object());
      fail("Processed a non-serializable object.");
    } catch (IllegalArgumentException e) {
    }
  }

  public void testCompressedStringNotSmaller() throws Exception {
    String s1 = "This is a test simple string that will not be compressed.";
    tc.setCompressionThreshold(8);
    CachedData cd = tc.encode(s1);
    assertEquals(WhalinTranscoder.SPECIAL_STRING, cd.getFlags());
    assertTrue(Arrays.equals(s1.getBytes(), cd.getData()));
    assertEquals(s1, tc.decode(cd));
  }

  public void testCompressedString() throws Exception {
    String s1 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    tc.setCompressionThreshold(8);
    CachedData cd = tc.encode(s1);
    assertEquals(WhalinTranscoder.COMPRESSED | WhalinTranscoder.SPECIAL_STRING,
        cd.getFlags());
    assertFalse(Arrays.equals(s1.getBytes(), cd.getData()));
    assertEquals(s1, tc.decode(cd));
  }

  public void testObject() throws Exception {
    Calendar c = Calendar.getInstance();
    CachedData cd = tc.encode(c);
    assertEquals(WhalinTranscoder.SERIALIZED, cd.getFlags());
    assertEquals(c, tc.decode(cd));
  }

  public void testCompressedObject() throws Exception {
    tc.setCompressionThreshold(8);
    Calendar c = Calendar.getInstance();
    CachedData cd = tc.encode(c);
    assertEquals(WhalinTranscoder.SERIALIZED | WhalinTranscoder.COMPRESSED,
        cd.getFlags());
    assertEquals(c, tc.decode(cd));
  }

  public void testUnencodeable() throws Exception {
    try {
      CachedData cd = tc.encode(new Object());
      fail("Should fail to serialize, got" + cd);
    } catch (IllegalArgumentException e) {
    }
  }

  public void testUndecodeable() throws Exception {
    CachedData cd = new CachedData(Integer.MAX_VALUE
        & ~(WhalinTranscoder.COMPRESSED | WhalinTranscoder.SERIALIZED),
        tu.encodeInt(Integer.MAX_VALUE), tc.getMaxSize());
    assertNull(tc.decode(cd));
  }

  public void testUndecodeableSerialized() throws Exception {
    CachedData cd = new CachedData(WhalinTranscoder.SERIALIZED,
        tu.encodeInt(Integer.MAX_VALUE), tc.getMaxSize());
    assertNull(tc.decode(cd));
  }

  public void testUndecodeableCompressed() throws Exception {
    CachedData cd = new CachedData(WhalinTranscoder.COMPRESSED,
        tu.encodeInt(Integer.MAX_VALUE), tc.getMaxSize());
    assertNull(tc.decode(cd));
  }

  @Override
  protected int getStringFlags() {
    return WhalinTranscoder.SPECIAL_STRING;
  }
