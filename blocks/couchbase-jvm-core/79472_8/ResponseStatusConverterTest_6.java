
    @Test
    public void shouldHandleCheckDetailsForBinaryWithoutFlag() {
        assertNull(ResponseStatusConverter.detailsFromBinary((byte) 0x00, Unpooled.buffer())); // raw
        assertNull(ResponseStatusConverter.detailsFromBinary((byte) 0x02, Unpooled.buffer())); // snappy
        assertNull(ResponseStatusConverter.detailsFromBinary((byte) 0x04, Unpooled.buffer())); // xattr
        assertNull(ResponseStatusConverter.detailsFromBinary((byte) (0x04 & 0x02), Unpooled.buffer())); // snappy & xattr
    }

    @Test
    public void shouldHandleCheckDetailsForBinaryWithFlag() {
        String raw = "{ \"error\" : { \"context\" : \"textual context information\", \"ref\" :" +
            " \"error reference to be found in the server logs\" }}";

        assertNotNull(ResponseStatusConverter.detailsFromBinary((byte) 0x01,
            Unpooled.copiedBuffer(raw, CharsetUtil.UTF_8)));
        assertNotNull(ResponseStatusConverter.detailsFromBinary((byte) (0x01 | 0x02),
            Unpooled.copiedBuffer(raw, CharsetUtil.UTF_8)));
        assertNotNull(ResponseStatusConverter.detailsFromBinary((byte) (0x01 | 0x04 | 0x02),
                Unpooled.copiedBuffer(raw, CharsetUtil.UTF_8)));
    }
