    @Test
    public void testDropPrimaryIndexWithCustomNameIsASimpleDrop() {
        Statement dropCustom1 = dropNamedPrimaryIndex("a", "b", "c");
        Statement dropCustom2 = Index.dropNamedPrimaryIndex("b", "c");

        assertEquals("DROP INDEX `a`:`b`.`c`", dropCustom1.toString());
        assertEquals("DROP INDEX `b`.`c`", dropCustom2.toString());
    }

