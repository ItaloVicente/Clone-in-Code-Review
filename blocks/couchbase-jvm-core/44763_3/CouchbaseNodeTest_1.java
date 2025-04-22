    @Test
    public void shouldBeEqualOnSameInetAddr() throws Exception {
        CouchbaseNode node1 = new CouchbaseNode(InetAddress.getByName("1.2.3.4"), environment, null);
        CouchbaseNode node2 = new CouchbaseNode(InetAddress.getByName("1.2.3.4"), environment, null);
        assertEquals(node1, node2);
        assertEquals(node1.hashCode(), node2.hashCode());
    }

    @Test
    public void shouldNotBeEqualOnDifferentInetAddr() throws Exception {
        CouchbaseNode node1 = new CouchbaseNode(InetAddress.getByName("1.2.3.4"), environment, null);
        CouchbaseNode node2 = new CouchbaseNode(InetAddress.getByName("2.3.4.5"), environment, null);
        assertNotEquals(node1, node2);
    }

