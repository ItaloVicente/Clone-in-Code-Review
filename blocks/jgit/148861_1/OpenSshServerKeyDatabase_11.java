	private Collection<SshdSocketAddress> getCandidates(
			@NonNull String connectAddress
			@NonNull InetSocketAddress remoteAddress) {
		Collection<SshdSocketAddress> candidates = new TreeSet<>(
				SshdSocketAddress.BY_HOST_AND_PORT);
		candidates.add(SshdSocketAddress.toSshdSocketAddress(remoteAddress));
		SshdSocketAddress address = toSshdSocketAddress(connectAddress);
		if (address != null) {
			candidates.add(address);
		}
		return candidates;
	}

	private String createHostKeyLine(Collection<SshdSocketAddress> patterns
			PublicKey key
		StringBuilder result = new StringBuilder();
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
