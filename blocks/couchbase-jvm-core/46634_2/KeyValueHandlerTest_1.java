    @Test
    public void testShortResponseCodesAreCorrectlyMapped() {
        assertEquals(ResponseStatus.SUCCESS, KeyValueHandler.convertStatus((short) 0x00));
        assertEquals(ResponseStatus.NOT_EXISTS, KeyValueHandler.convertStatus((short) 0x01));
        assertEquals(ResponseStatus.EXISTS, KeyValueHandler.convertStatus((short) 0x02));
        assertEquals(ResponseStatus.TOO_BIG, KeyValueHandler.convertStatus((short) 0x03));
        assertEquals(ResponseStatus.INVALID_ARGUMENTS, KeyValueHandler.convertStatus((short) 0x04));
        assertEquals(ResponseStatus.NOT_STORED, KeyValueHandler.convertStatus((short) 0x05));
        assertEquals(ResponseStatus.INVALID_ARGUMENTS, KeyValueHandler.convertStatus((short) 0x06));
        assertEquals(ResponseStatus.RETRY, KeyValueHandler.convertStatus((short) 0x07));
        assertEquals(ResponseStatus.COMMAND_UNAVAILABLE, KeyValueHandler.convertStatus((short) 0x81));
        assertEquals(ResponseStatus.OUT_OF_MEMORY, KeyValueHandler.convertStatus((short) 0x82));
        assertEquals(ResponseStatus.COMMAND_UNAVAILABLE, KeyValueHandler.convertStatus((short) 0x83));
        assertEquals(ResponseStatus.INTERNAL_ERROR, KeyValueHandler.convertStatus((short) 0x84));
        assertEquals(ResponseStatus.TEMPORARY_FAILURE, KeyValueHandler.convertStatus((short) 0x85));
        assertEquals(ResponseStatus.TEMPORARY_FAILURE, KeyValueHandler.convertStatus((short) 0x86));

        assertEquals(ResponseStatus.FAILURE, KeyValueHandler.convertStatus(Short.MAX_VALUE));
    }

