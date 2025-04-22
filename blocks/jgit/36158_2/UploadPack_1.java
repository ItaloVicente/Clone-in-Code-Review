					boolean serverBehavior = options.containsAll(
							advertisedCapabilities);
					if (!serverBehavior) {
						throw new PackProtocolException(JGitText
								.get().nonadvertisedCapabilitesRequested);
					}
