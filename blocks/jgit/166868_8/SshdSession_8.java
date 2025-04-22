	private ClientSession connect(HostConfigEntry config
			AttributeRepository context
			throws IOException {
		ConnectFuture connected = client.connect(config
		if (!useTimeout) {
			connected = connected.verify();
		} else {
			long start = System.currentTimeMillis();
			connected = connected.verify(timeout[0]);
			timeout[0] -= System.currentTimeMillis() - start;
			if (timeout[0] <= 0) {
				timeout[0] = 10;
			}
		}
		return connected.getSession();
	}

	private void close(Closeable toClose
		if (toClose != null) {
			try {
				toClose.close();
			} catch (IOException e) {
				error.addSuppressed(e);
			}
		}
	}

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

	private List<URIish> determineHops(List<URIish> currentHops
			HostConfigEntry hostConfig
		if (currentHops.isEmpty()) {
			String jumpHosts = hostConfig.getProperty(SshConstants.PROXY_JUMP);
			if (!StringUtils.isEmptyOrNull(jumpHosts)) {
				try {
					return parseProxyJump(jumpHosts);
				} catch (URISyntaxException e) {
					throw new IOException(
							format(SshdText.get().configInvalidProxyJump
									jumpHosts)
							e);
				}
			}
		}
		return currentHops;
	}

	private List<URIish> parseProxyJump(String proxyJump)
			throws URISyntaxException {
		String[] hops = proxyJump.split("
			}
			URIish to = new URIish(hop);
			if (!SshConstants.SSH_SCHEME.equalsIgnoreCase(to.getScheme())) {
				throw new URISyntaxException(hop
						SshdText.get().configProxyJumpNotSsh);
			} else if (!StringUtils.isEmptyOrNull(to.getPath())) {
				throw new URISyntaxException(hop
						SshdText.get().configProxyJumpWithPath);
			}
			result.add(to);
		}
		return result;
	}

