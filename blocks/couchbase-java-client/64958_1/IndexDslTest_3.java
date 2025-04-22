        assertEquals(expectedNodes, path.withNodes("test1", "test2").toString());
        assertEquals(expectedNodes, path.withNodes(Arrays.asList("test1", "test2")).toString());
        assertEquals(expectedDeferAndNodes, path.withDeferAndNodes("test1", "test2").toString());
        assertEquals(expectedDeferAndNodes, path.withDeferAndNodes(Arrays.asList("test1", "test2")).toString());
    }

    @Test
    public void testWithEmptyNodesVariants() {
        WithPath path = new DefaultWithPath(null);

        String expected = "WITH {}";
        String expectedDefer = "WITH " + JsonObject.create().put("defer_build", true);

        assertEquals(expected, path.withNode(null).toString());
        assertEquals(expected, path.withNodes().toString());
        assertEquals(expected, path.withNodes(Collections.<String>emptyList()).toString());
        assertEquals(expected, path.withNodes((Collection<String>) null).toString());
        assertEquals(expected, path.withNodes((String[]) null).toString());

        assertEquals(expectedDefer, path.withDeferAndNode(null).toString());
        assertEquals(expectedDefer, path.withDeferAndNodes().toString());
        assertEquals(expectedDefer, path.withDeferAndNodes(Collections.<String>emptyList()).toString());
        assertEquals(expectedDefer, path.withDeferAndNodes((Collection<String>) null).toString());
        assertEquals(expectedDefer, path.withDeferAndNodes((String[]) null).toString());
