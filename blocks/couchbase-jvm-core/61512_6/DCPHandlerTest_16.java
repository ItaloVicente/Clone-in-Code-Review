        assertEquals(DCPHandler.OP_STREAM_REQUEST, outbound.getOpcode());
        assertEquals(0, outbound.getKeyLength());
        assertEquals(48, outbound.getTotalBodyLength());
        assertEquals(1234, outbound.getReserved());
        assertEquals(48, outbound.getExtrasLength());
        assertEquals(0, outbound.getExtras().readInt());        // flags
        assertEquals(0, outbound.getExtras().readInt());        // reserved
        assertEquals(9012, outbound.getExtras().readLong());    // start sequence number
        assertEquals(3456, outbound.getExtras().readLong());    // end sequence number
        assertEquals(5678, outbound.getExtras().readLong());    // vbucket UUID
        assertEquals(7890, outbound.getExtras().readLong());    // snapshot start sequence number
        assertEquals(12345, outbound.getExtras().readLong());   // snapshot end sequence number
