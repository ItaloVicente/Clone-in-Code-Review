	public void testStandardFormat_SmallObject_TruncatedZLibStream()
			throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = rng.nextBytes(300);

		try {
			byte[] gz = compressStandardFormat(Constants.OBJ_BLOB
			byte[] tr = new byte[gz.length - 1];
			System.arraycopy(gz
			UnpackedObject.open(new ByteArrayInputStream(tr)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	public void testStandardFormat_SmallObject_TrailingGarbage()
			throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = rng.nextBytes(300);

		try {
			byte[] gz = compressStandardFormat(Constants.OBJ_BLOB
			byte[] tr = new byte[gz.length + 1];
			System.arraycopy(gz
			UnpackedObject.open(new ByteArrayInputStream(tr)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

