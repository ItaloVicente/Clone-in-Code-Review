        assertEquals(ResponseStatus.SUCCESS, ResponseStatusConverter.fromBinary((short) 0x00));
        assertEquals(ResponseStatus.NOT_EXISTS, ResponseStatusConverter.fromBinary((short) 0x01));
        assertEquals(ResponseStatus.EXISTS, ResponseStatusConverter.fromBinary((short) 0x02));
        assertEquals(ResponseStatus.TOO_BIG, ResponseStatusConverter.fromBinary((short) 0x03));
        assertEquals(ResponseStatus.INVALID_ARGUMENTS, ResponseStatusConverter.fromBinary((short) 0x04));
        assertEquals(ResponseStatus.NOT_STORED, ResponseStatusConverter.fromBinary((short) 0x05));
        assertEquals(ResponseStatus.INVALID_ARGUMENTS, ResponseStatusConverter.fromBinary((short) 0x06));
        assertEquals(ResponseStatus.RETRY, ResponseStatusConverter.fromBinary((short) 0x07));
        assertEquals(ResponseStatus.COMMAND_UNAVAILABLE, ResponseStatusConverter.fromBinary((short) 0x81));
        assertEquals(ResponseStatus.OUT_OF_MEMORY, ResponseStatusConverter.fromBinary((short) 0x82));
        assertEquals(ResponseStatus.COMMAND_UNAVAILABLE, ResponseStatusConverter.fromBinary((short) 0x83));
        assertEquals(ResponseStatus.INTERNAL_ERROR, ResponseStatusConverter.fromBinary((short) 0x84));
        assertEquals(ResponseStatus.SERVER_BUSY, ResponseStatusConverter.fromBinary((short) 0x85));
        assertEquals(ResponseStatus.TEMPORARY_FAILURE, ResponseStatusConverter.fromBinary((short) 0x86));

        assertEquals(ResponseStatus.FAILURE, ResponseStatusConverter.fromBinary(Short.MAX_VALUE));
