	private HostConfigEntry getHostConfig(String username
			int port) throws IOException {
		HostConfigEntry entry = client.getHostConfigEntryResolver()
				.resolveEffectiveHost(host
		if (entry == null) {
			if (SshdSocketAddress.isIPv6Address(host)) {
				return new HostConfigEntry(""
			}
			return new HostConfigEntry(host
		}
		return entry;
	}

	private List<URIish> parseProxyJump(String proxyJump)
			throws URISyntaxException {
		String[] hops = proxyJump.split("
			}
			URIish to = new URIish(hop);
			result.add(to);
		}
		return result;
	}

