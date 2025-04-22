
    ComplexKey singleFloat = ComplexKey.of(3.141159f);
    assertEquals("3.141159", singleFloat.toJson());

    ComplexKey singleDouble = ComplexKey.of(3.141159);
    assertEquals("3.141159", singleDouble.toJson());
