
    @Test
    public void testAnyAndEveryIn() throws Exception {
        Expression comprehension = Collections.anyAndEveryIn("child", x("tutorial.children")).satisfies(x("child.age").gt(10));
        assertEquals("ANY AND EVERY child IN tutorial.children SATISFIES child.age > 10 END", comprehension.toString());
    }
