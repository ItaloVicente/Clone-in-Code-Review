    @Test
    public void shouldEncodeUtf8() {
        String simple = "simpleValue";
        String rune = "ᚠᛇᚻ᛫ᛒᛦᚦ᛫ᚠᚱᚩᚠᚢᚱ᛫ᚠᛁᚱᚪ᛫ᚷᛖᚻᚹᛦᛚᚳᚢᛗ";
        String greek = "Τη γλώσσα μου έδωσαν ελληνική";
        String russian = "На берегу пустынных волн";

        assertEquals(
            Unpooled.wrappedBuffer(simple.getBytes(CharsetUtil.UTF_8)),
            TranscoderUtils.encodeStringAsUtf8(simple)
        );

        assertEquals(
            Unpooled.wrappedBuffer(rune.getBytes(CharsetUtil.UTF_8)),
            TranscoderUtils.encodeStringAsUtf8(rune)
        );

        assertEquals(
            Unpooled.wrappedBuffer(greek.getBytes(CharsetUtil.UTF_8)),
            TranscoderUtils.encodeStringAsUtf8(greek)
        );

        assertEquals(
            Unpooled.wrappedBuffer(russian.getBytes(CharsetUtil.UTF_8)),
            TranscoderUtils.encodeStringAsUtf8(russian)
        );
    }

