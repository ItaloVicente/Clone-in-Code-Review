		if (defaultRevision == null && defaultRemote != null) {
			Remote remote = remotes.get(defaultRemote);
			if (remote != null && remote.revision != null) {
				defaultRevision = remote.revision;
			}
		}
