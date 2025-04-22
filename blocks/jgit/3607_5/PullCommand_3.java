			if (!config.getURIs().isEmpty())
				remoteUri = config.getURIs().get(0).toString();
			else
				try {
					remoteUri = new URIish(remote).toString();
				} catch (URISyntaxException e) {
					throw new InvalidConfigurationException(
							MessageFormat.format(JGitText.get().invalidRemote
									remote)
				}
