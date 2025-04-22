
    expResult = "[\"Hello\",\"World\",5.12]";
    result = ComplexKey.of("Hello", "World", 5.12);
    assertEquals(expResult, result.toJson());

    expResult = "[true,false]";
    result = ComplexKey.of(true, false);
    assertEquals(expResult, result.toJson());
