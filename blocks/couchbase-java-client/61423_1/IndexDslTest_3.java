    @Test
    public void testDropPrimaryIndexWithCustomNameIsASimpleDrop() {
        Statement dropCustom1 = dropCustomPrimaryIndex("a", "b", "c");
        Statement dropCustom2 = dropCustomPrimaryIndex("b", "c");

        assertEquals("DROP INDEX `a`:`b`.`c`", dropCustom1.toString());
        assertEquals("DROP INDEX `b`.`c`", dropCustom2.toString());
    }

