    @Test
    public void shouldEncodeReplicaGetRequest() {
        ReplicaGetRequest request = new ReplicaGetRequest("key", "bucket", (short) 1);
        request.partition((short) 512);

        channel.writeOutbound(request);
        BinaryMemcacheRequest outbound = (BinaryMemcacheRequest) channel.readOutbound();
        assertNotNull(outbound);
        assertEquals("key", outbound.getKey());
        assertEquals("key".length(), outbound.getKeyLength());
        assertEquals("key".length(), outbound.getTotalBodyLength());
        assertEquals(512, outbound.getReserved());
        assertEquals(KeyValueHandler.OP_GET_REPLICA, outbound.getOpcode());
        assertEquals(0, outbound.getExtrasLength());
    }

