    query.setKey(ComplexKey.of(3.141159f));
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=3.141159", result);

    query.setKey(ComplexKey.of(3.141159));
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=3.141159", result);

