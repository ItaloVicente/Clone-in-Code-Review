		StringBuilder knownHostsLine = new StringBuilder();
		knownHostsLine.append("[localhost]:").append(testPort).append(' ');
		PublicKeyEntry.appendPublicKeyEntry(knownHostsLine
				hostKey.getPublic());
		Files.write(knownHosts.toPath()
				Collections.singleton(knownHostsLine.toString()));
