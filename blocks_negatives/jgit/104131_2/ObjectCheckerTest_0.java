		checker = new ObjectChecker() {
			@Override
			public void checkBlob(byte[] raw) throws CorruptObjectException {
				String in = decode(raw);
				if (in.contains("secret_key")) {
					throw new CorruptObjectException("don't add a secret key");
				}
			}
		};
		checker.check(OBJ_BLOB, encodeASCII("key = \"public_key\""));
