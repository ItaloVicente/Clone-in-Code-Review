
			HashSet<String> opts = new HashSet<>();
				if (clientCapability.startsWith(AGENT_PREFIX)) {
					agent = clientCapability.substring(AGENT_PREFIX.length());
				} else {
					opts.add(clientCapability);
				}
			}
