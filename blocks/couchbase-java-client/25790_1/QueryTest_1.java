
    query.setKey("123");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=123", result);

    query.setKey("123FOUR");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?key=\"123FOUR\"", result);
