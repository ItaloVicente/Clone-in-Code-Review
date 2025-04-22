		if (config.getHashKnownHosts()) {
			NamedFactory<Mac> digester = KnownHostDigest.SHA1;
			Mac mac = digester.create();
			SecureRandom prng = new SecureRandom();
			byte[] salt = new byte[mac.getDefaultBlockSize()];
			for (SshdSocketAddress address : patterns) {
				if (result.length() > 0) {
					result.append('
				}
				prng.nextBytes(salt);
				KnownHostHashValue.append(result
						KnownHostHashValue.calculateHashValue(
								address.getHostName()
								salt));
			}
		} else {
			for (SshdSocketAddress address : patterns) {
				if (result.length() > 0) {
					result.append('
				}
				KnownHostHashValue.appendHostPattern(result
						address.getHostName()
