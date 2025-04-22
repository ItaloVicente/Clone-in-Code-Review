	public void testStandardFormat_LargeObject_TruncatedZLibStream()
			throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = rng.nextBytes(ObjectLoader.STREAM_THRESHOLD + 5);
		ObjectId id = new ObjectInserter.Formatter().idFor(type
		byte[] gz = compressStandardFormat(type
		byte[] tr = new byte[gz.length - 1];
		System.arraycopy(gz

		write(id

		ObjectLoader ol;
		{
			FileInputStream fs = new FileInputStream(path(id));
			try {
				ol = UnpackedObject.open(fs
			} finally {
				fs.close();
			}
		}

		byte[] tmp = new byte[data.length];
		InputStream in = ol.openStream();
		IO.readFully(in
		try {
			in.close();
			fail("close did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	public void testStandardFormat_LargeObject_TrailingGarbage()
			throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = rng.nextBytes(ObjectLoader.STREAM_THRESHOLD + 5);
		ObjectId id = new ObjectInserter.Formatter().idFor(type
		byte[] gz = compressStandardFormat(type
		byte[] tr = new byte[gz.length + 1];
		System.arraycopy(gz

		write(id

		ObjectLoader ol;
		{
			FileInputStream fs = new FileInputStream(path(id));
			try {
				ol = UnpackedObject.open(fs
			} finally {
				fs.close();
			}
		}

		byte[] tmp = new byte[data.length];
		InputStream in = ol.openStream();
		IO.readFully(in
		try {
			in.close();
			fail("close did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

