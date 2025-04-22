  @Test
  public void testNumericValues() {
    ComplexKey singleInt = ComplexKey.of(4444);
    assertEquals("4444", singleInt.toJson());

    ComplexKey singleLong = ComplexKey.of(99999999999L);
    assertEquals("99999999999", singleLong.toJson());
  }

