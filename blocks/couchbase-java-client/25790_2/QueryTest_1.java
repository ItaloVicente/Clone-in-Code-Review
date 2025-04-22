
    query.setKey("123");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=123", result);

    query.setKey("123FOUR");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=\"123FOUR\"", result);

    query.setKey("123");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=123", result);

    query.setKey("1234.9");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=1234.9", result);

    query.setKey("1,234.9");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=1234.9", result);

    query.setKey("0.123");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=0.123", result);

    query.setKey(".123");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=0.123", result);

    query.setKey("123E10");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=1230000000000", result);

    query.setKey("\"123E10\"");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=\"123E10\"", result);

