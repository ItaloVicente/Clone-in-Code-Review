		public boolean createNewFile(Path path) {
			if (provider == null) {
				return false;
			}
			URIish uri = new URIish().setPath(path.toString());
			return askUser(provider
					format(SshdText.get().knownHostsUserAskCreationPrompt
							path)
					format(SshdText.get().knownHostsUserAskCreationMsg
		}
