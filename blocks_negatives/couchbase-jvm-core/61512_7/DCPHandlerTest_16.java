        assertEquals(connectionName, new String(outbound.getKey(), CharsetUtil.UTF_8));
        assertEquals(connectionName.length(), outbound.getKeyLength());
        assertEquals(connectionName.length() + 8, outbound.getTotalBodyLength());
        assertEquals(1, outbound.getReserved());
        assertEquals(DCPHandler.OP_OPEN_CONNECTION, outbound.getOpcode());
        assertEquals(8, outbound.getExtrasLength());
        assertEquals(42, outbound.getExtras().readInt());
        assertEquals(1, outbound.getExtras().readInt());
