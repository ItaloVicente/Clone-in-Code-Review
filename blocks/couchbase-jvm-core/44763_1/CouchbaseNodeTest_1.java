    @Test
    public void shouldBeEqualOnSameInetAddr() {
        CouchbaseNode node1 = new CouchbaseNode(host, environment, null);
        CouchbaseNode node2 = new CouchbaseNode(host, environment, null);
        assertEquals(node1, node2);
    }

