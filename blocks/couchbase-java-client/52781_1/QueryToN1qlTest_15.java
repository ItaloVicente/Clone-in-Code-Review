        Pattern p = Pattern.compile("PREPARE \\w+ FROM ");
        Matcher prepareMatcher = p.matcher(prepare.toString());
        Matcher prepareFromStringMatcher = p.matcher(prepareFromString.toString());

        assertTrue(prepareMatcher.find());
        assertTrue(prepareFromStringMatcher.find());
        assertEquals("SELECT * FROM default", prepareMatcher.replaceAll(""));
        assertEquals("SELECT * FROM default", prepareFromStringMatcher.replaceAll(""));
