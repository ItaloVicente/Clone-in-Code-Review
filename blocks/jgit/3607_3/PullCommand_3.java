			if (!config.getURIs().isEmpty())
				remoteUri = config.getURIs().get(0).toPrivateString();
			else
				try {
					new URIish(remote);
					remoteUri = remote;
				} catch (URISyntaxException e) {
					throw new InvalidConfigurationException(
							MessageFormat.format(JGitText.get().invalidRemote
									remote)
				}
