	@Test
	public void testLineMapOrBinary() throws Exception {
		final byte[] buf = "xxxfoo\nb\0ar".getBytes(ISO_8859_1);
		exception.expect(BinaryBlobException.class);
		RawParseUtils.lineMapOrBinary(buf
	}

