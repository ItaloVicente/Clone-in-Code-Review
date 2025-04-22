	@Test
	public void testCheckBlobNotCorrupt() throws CorruptObjectException {
		checker = new ObjectChecker() {
			@Override
			public void checkBlob(byte[] raw) throws CorruptObjectException {
				String in = decode(raw);
				if (in.contains("secret_key")) {
					throw new CorruptObjectException("don't add a secret key");
				}
			}
		};
		checker.check(OBJ_BLOB
	}

	@Test
	public void testCheckBlobCorrupt() throws CorruptObjectException {
		checker = new ObjectChecker() {
			@Override
			public void checkBlob(byte[] raw) throws CorruptObjectException {
				String in = decode(raw);
				if (in.contains("secret_key")) {
					throw new CorruptObjectException("don't add a secret key");
				}
			}
		};
		thrown.expect(CorruptObjectException.class);
		checker.check(OBJ_BLOB
	}

	@Test
	public void testCheckBlobWithBlobObjectCheckerNotCorrupt()
			throws CorruptObjectException {
		checker = new ObjectChecker() {
			@Override
			public BlobObjectChecker newBlobObjectChecker() {
				return new BlobObjectChecker() {
					private boolean containSecretKey;

					@Override
					public void update(byte[] in
						String str = decode(in
						if (str.contains("secret_key")) {
							containSecretKey = true;
						}
					}

					@Override
					public void endBlob(AnyObjectId id)
						throws CorruptObjectException {
						if (containSecretKey) {
							throw new CorruptObjectException(
									"don't add a secret key");
						}
					}
				};
			}
		};
		checker.check(OBJ_BLOB
	}

	@Test
	public void testCheckBlobWithBlobObjectCheckerCorrupt()
			throws CorruptObjectException {
		checker = new ObjectChecker() {
			@Override
			public BlobObjectChecker newBlobObjectChecker() {
				return new BlobObjectChecker() {
					private boolean containSecretKey;

					@Override
					public void update(byte[] in
						String str = decode(in
						if (str.contains("secret_key")) {
							containSecretKey = true;
						}
					}

					@Override
					public void endBlob(AnyObjectId id)
						throws CorruptObjectException {
						if (containSecretKey) {
							throw new CorruptObjectException(
									"don't add a secret key");
						}
					}
				};
			}
		};
		thrown.expect(CorruptObjectException.class);
		checker.check(OBJ_BLOB
	}

