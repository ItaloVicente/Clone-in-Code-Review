	private void loadKnownRemoteRefs() {
		try {
			Set<String> remotes = repository.getRefDatabase()
					.getRefs(Constants.R_REMOTES).keySet();
			for (String remote : remotes) {
				int slashIndex = remote.indexOf('/');
				if (slashIndex > 0 && slashIndex < remote.length() - 1) {
					knownRemoteRefs.add(remote.substring(slashIndex + 1));
				}
			}
		} catch (IOException e) {
		}
	}

