  public void testIntegerDecode() {
    assertEquals(129, decodeInt(new byte[] { 0, 0, 0, (byte) 0x81 }, 0));
    assertEquals(129 * 256, decodeInt(new byte[] { 0, 0, (byte) 0x81, 0 }, 0));
    assertEquals(129 * 256 * 256,
        decodeInt(new byte[] { 0, (byte) 0x81, 0, 0 }, 0));
    assertEquals(129 * 256 * 256 * 256,
        decodeInt(new byte[] { (byte) 0x81, 0, 0, 0 }, 0));
  }
