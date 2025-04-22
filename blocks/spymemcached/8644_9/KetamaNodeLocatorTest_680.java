  public void testLibKetamaCompat() {
    setupNodes(5);
    assertPosForKey("36", 2);
    assertPosForKey("10037", 3);
    assertPosForKey("22051", 1);
    assertPosForKey("49044", 4);
  }
