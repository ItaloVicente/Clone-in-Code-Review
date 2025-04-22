		checker = new ObjectChecker() {
			@Override
			public BlobObjectChecker newBlobObjectChecker() {
				return new BlobObjectChecker() {
					private boolean containSecretKey;

					@Override
					public void update(byte[] in, int offset, int len) {
						String str = decode(in, offset, offset + len);
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
