    @Test
    public void testCreatePrimaryIndexWithCustomName() throws Exception {
        OnPrimaryPath idx1 = Index.createNamedPrimaryIndex("a");
        assertFalse(idx1 instanceof Statement);

        UsingWithPath idx2 = idx1.on("beer-sample");
        assertTrue(idx2 instanceof Statement);

        Statement fullIndex = Index.createNamedPrimaryIndex("def_primary")
            .on("beer-sample")
            .using(IndexType.GSI)
            .withDefer();

        assertEquals("CREATE PRIMARY INDEX `def_primary` ON `beer-sample` " +
                "USING GSI WITH {\"defer_build\":true}", fullIndex.toString());
    }

