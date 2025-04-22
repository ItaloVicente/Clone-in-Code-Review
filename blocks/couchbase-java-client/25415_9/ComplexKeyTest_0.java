    Object[] expResult = new Object[] {null};
    ComplexKey singleNull = ComplexKey.of(expResult);
    String aNullJsonString = singleNull.toJson();
    assertEquals("null", aNullJsonString);
  }

  @Test
  public void testOnlyNullValue() {
    Object expResult = null;
    ComplexKey singleNull = ComplexKey.of(expResult);
