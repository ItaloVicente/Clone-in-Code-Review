    @Test
    public void testArrayFlatten() throws Exception {
        Expression e1 = ArrayFunctions.arrayFlatten(x(1), 1);
        Expression e2 = ArrayFunctions.arrayFlatten("1", 1);
        Expression e3 = ArrayFunctions.arrayFlatten(ARRAY, 1);

        assertEquals(e1.toString(), e2.toString());
        assertEquals("ARRAY_FLATTEN(1, 1)", e1.toString());
        assertEquals("ARRAY_FLATTEN([1,true], 1)", e3.toString());
    }

