
    @Test
    public void testDropIndex() {
        String drop1 = dropIndex("a", "b", "c").using(IndexType.GSI).toString();
        String drop2 = dropIndex("b", "c").using(IndexType.GSI).toString();
        String drop3 = dropPrimaryIndex("a", "b").using(IndexType.GSI).toString();
        String drop4 = dropPrimaryIndex("b").using(IndexType.VIEW).toString();

        assertEquals("DROP INDEX `a`:`b`.`c` USING GSI", drop1);
        assertEquals("DROP INDEX `b`.`c` USING GSI", drop2);
        assertEquals("DROP PRIMARY INDEX ON `a`:`b` USING GSI", drop3);
        assertEquals("DROP PRIMARY INDEX ON `b` USING VIEW", drop4);
    }

    @Test
    public void testDropIndexWithoutUsing() {
        Statement drop1 = dropIndex("a", "b", "c");
        Statement drop2 = dropIndex("b", "c");
        Statement drop3 = dropPrimaryIndex("a", "b");
        Statement drop4 = dropPrimaryIndex("b");

        assertEquals("DROP INDEX `a`:`b`.`c`", drop1.toString());
        assertEquals("DROP INDEX `b`.`c`", drop2.toString());
        assertEquals("DROP PRIMARY INDEX ON `a`:`b`", drop3.toString());
        assertEquals("DROP PRIMARY INDEX ON `b`", drop4.toString());
    }

    @Test
    public void testBuildIndex() {
        Statement build1 = buildIndex().on("test").primary().using(IndexType.GSI);
        Statement build2 = buildIndex().on("prefix", "test").indexes("a").using(IndexType.GSI);
        Statement build3 = buildIndex().on(null, "test").indexes("a", "b", "c");

        assertEquals("BUILD INDEX ON `test` (`" + Index.PRIMARY_NAME + "`) USING GSI", build1.toString());
        assertEquals("BUILD INDEX ON `prefix`:`test` (`a`) USING GSI", build2.toString());
        assertEquals("BUILD INDEX ON `test` (`a`, `b`, `c`)", build3.toString());
    }
