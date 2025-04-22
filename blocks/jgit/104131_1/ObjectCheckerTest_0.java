	private static final ObjectChecker OBJECT_CHECKER = new ObjectChecker() {
		@Override
		public void checkBlob(byte[] raw) throws CorruptObjectException {
			String in = decode(raw);
			if (in.contains("secret_key")) {
				throw new CorruptObjectException("don't add a secret key");
			}
		}
	};

	private static final ObjectChecker BLOB_OBJECT_CHECKER = new ObjectChecker() {
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

