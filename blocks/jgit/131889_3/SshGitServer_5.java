	public void addHostKey(@NonNull Path key
			throws IOException
		try (InputStream in = Files.newInputStream(key)) {
			KeyPair pair = SecurityUtils.loadKeyPairIdentity(key.toString()
					null);
			if (inFront) {
				hostKeys.add(0
			} else {
				hostKeys.add(pair);
			}
		}
	}

