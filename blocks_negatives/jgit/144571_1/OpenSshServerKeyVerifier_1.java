		if (!Files.exists(path)) {
			if (askAboutNewFile) {
				CredentialsProvider provider = getCredentialsProvider(
						clientSession);
				if (provider == null) {
					return;
				}
				URIish uri = new URIish().setPath(path.toString());
				if (!askUser(provider, uri, //
						format(SshdText.get().knownHostsUserAskCreationPrompt,
								path), //
						format(SshdText.get().knownHostsUserAskCreationMsg,
								path))) {
					return;
				}
			}
		}
