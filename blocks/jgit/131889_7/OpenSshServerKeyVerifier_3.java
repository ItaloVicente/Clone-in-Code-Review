		return filesToUse;
	}

	@Override
	public List<HostEntryPair> lookup(ClientSession session
			SocketAddress remote) {
		List<HostKeyFile> filesToUse = getFilesToUse(session);
		HostKeyHelper helper = new HostKeyHelper();
		List<HostEntryPair> result = new ArrayList<>();
		Collection<SshdSocketAddress> candidates = helper
				.resolveHostNetworkIdentities(session
		for (HostKeyFile file : filesToUse) {
			for (HostEntryPair current : file.get()) {
				KnownHostEntry entry = current.getHostEntry();
				for (SshdSocketAddress host : candidates) {
					if (entry.isHostMatch(host.getHostName()
						result.add(current);
						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean verifyServerKey(ClientSession clientSession
			SocketAddress remoteAddress
		List<HostKeyFile> filesToUse = getFilesToUse(clientSession);
