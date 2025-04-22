    @Test
    public void testHintIndexPathSingle() {
        Statement hint1 = new DefaultHintPath(null).useIndex(IndexReference.indexRef("test"));
        Statement hint2 = new DefaultHintPath(null).useIndex("test");


        assertEquals("USE INDEX (`test`)", hint1.toString());
        assertEquals(hint1.toString(), hint2.toString());

        Statement typedHint1 = new DefaultHintPath(null).useIndex(IndexReference.indexRef("test", IndexType.GSI));
        Statement typedHint2 = new DefaultHintPath(null).useIndex(IndexReference.indexRef("test", IndexType.VIEW));

        assertEquals("USE INDEX (`test` USING GSI)", typedHint1.toString());
        assertEquals("USE INDEX (`test` USING VIEW)", typedHint2.toString());
    }

    @Test
    public void testHintIndexPathMultiple() {
        Statement hint1 = new DefaultHintPath(null).useIndex(IndexReference.indexRef("test"), IndexReference.indexRef("test2"));
        Statement hint2 = new DefaultHintPath(null).useIndex("test", "test2");

        assertEquals("USE INDEX (`test`,`test2`)", hint1.toString());
        assertEquals(hint1.toString(), hint2.toString());

        Statement typedHint1 = new DefaultHintPath(null).useIndex(
                IndexReference.indexRef("test", IndexType.GSI),
                IndexReference.indexRef("test", IndexType.VIEW));

        assertEquals("USE INDEX (`test` USING GSI,`test` USING VIEW)", typedHint1.toString());
    }

