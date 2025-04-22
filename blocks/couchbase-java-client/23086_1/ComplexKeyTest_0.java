  @Test
  public void testForceArray() {
    ComplexKey simple = ComplexKey.of("40");
    assertEquals("\"40\"", simple.toJson());

    simple.forceArray(true);
    assertEquals("[\"40\"]", simple.toJson());
  }

