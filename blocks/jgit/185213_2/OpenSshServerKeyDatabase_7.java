				if (!isRevoked(entry)) {
					for (SshdSocketAddress host : candidates) {
						if (entry.isHostMatch(host.getHostName()
								host.getPort())) {
							result.add(current.getServerKey());
							break;
						}
