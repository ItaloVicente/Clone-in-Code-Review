				proxySession = connect(hop
			}
			AttributeRepository context = null;
			if (proxySession != null) {
				SshdSocketAddress remoteAddress = new SshdSocketAddress(host
						port);
				portForward = proxySession.createLocalPortForwardingTracker(
						SshdSocketAddress.LOCALHOST_ADDRESS
				context = AttributeRepository.ofKeyValuePair(
						JGitSshClient.LOCAL_FORWARD_ADDRESS
						portForward.getBoundAddress());
			}
			resultSession = connect(hostConfig
			if (proxySession != null) {
				final PortForwardingTracker tracker = portForward;
				final ClientSession pSession = proxySession;
				resultSession.addCloseFutureListener(future -> {
					IoUtils.closeQuietly(tracker);
					String sessionName = pSession.toString();
					try {
						pSession.close();
					} catch (IOException e) {
						LOG.error(format(
								SshdText.get().sshProxySessionCloseFailed
								sessionName)
					}
				});
				portForward = null;
				proxySession = null;
			}
			if (listener != null) {
				resultSession.addCloseFutureListener(listener);
			}
