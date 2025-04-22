	private Collection<SshdSocketAddress> getCandidates(
			SocketAddress connectAddress
		Collection<SshdSocketAddress> candidates = new TreeSet<>(
				SshdSocketAddress.BY_HOST_AND_PORT);
		candidates.add(SshdSocketAddress.toSshdSocketAddress(remoteAddress));
		candidates.add(SshdSocketAddress.toSshdSocketAddress(connectAddress));
		return candidates;
	}
