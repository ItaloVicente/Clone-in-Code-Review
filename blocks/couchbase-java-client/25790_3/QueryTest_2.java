
    query.setKeys("[\"123FOUR\",\"ABC4\",\"123\"]");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?keys=[\"123FOUR\",\"ABC4\",\"123\"]", result);

    query.setKeys("[\"123FOUR\", 123.4, 0.8]");
    result = URLDecoder.decode(query.toString(), "UTF-8");
    assertEquals("?keys=[\"123FOUR\", 123.4, 0.8]", result);
