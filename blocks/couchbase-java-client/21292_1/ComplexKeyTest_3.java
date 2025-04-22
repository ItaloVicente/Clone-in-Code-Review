  @Test
  public void testEmptyObject() {
    Object expResult = new Object();
    Object result = ComplexKey.emptyObject();
    assertEquals(expResult.getClass().getName(), result.getClass().getName());
  }

  @Test
  public void testDateInput() {
    Date start = new Date();
    Date end   = new Date();

    String expResult = "[\""+start.toString()+"\",\""+end.toString()+"\"]";
    ComplexKey result = ComplexKey.of(start, end);
    assertEquals(expResult, result.toJson());
  }

