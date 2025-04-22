  @Test
  public void testNumericValues() {
    ComplexKey singleInt = ComplexKey.of(4444);
    assertEquals("4444", singleInt.toJson());

    ComplexKey singleLong = ComplexKey.of(99999999999L);
    assertEquals("99999999999", singleLong.toJson());
  }

  @Ignore("Null argument not yet implemented") @Test
  public void testNullSingleValues() {
    ComplexKey singleNull = ComplexKey.of((Object[]) null); // NPE here
    String aNullJsonString = singleNull.toJson();
    assertEquals("null", aNullJsonString);
  }

  @Test
  public void testNullInArray() {
    ComplexKey withNull = ComplexKey.of("Matt", null);
    String wNullJsonString = withNull.toJson();
    assertEquals("[\"Matt\",null]", wNullJsonString);
  }

  @Test
  public void testBoolValues() {
    ComplexKey singleTrue = ComplexKey.of(true);
    assertEquals("true", singleTrue.toJson());

    ComplexKey arrBools = ComplexKey.of(true, false);
    assertEquals("[true,false]", arrBools.toJson());
  }


