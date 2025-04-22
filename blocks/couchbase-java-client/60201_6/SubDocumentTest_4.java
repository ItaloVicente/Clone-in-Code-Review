        assertTrue(resultSub.exists("sub"));
        assertTrue(resultInt.exists("int"));
        assertTrue(resultString.exists("string"));
        assertTrue(resultArray.exists("array"));
        assertTrue(resultBoolean.exists("boolean"));

        assertEquals(Boolean.TRUE, resultSub.content("sub"));
        assertEquals(Boolean.TRUE, resultInt.content("int"));
        assertEquals(Boolean.TRUE, resultString.content("string"));
        assertEquals(Boolean.TRUE, resultArray.content("array"));
        assertEquals(Boolean.TRUE, resultBoolean.content("boolean"));
