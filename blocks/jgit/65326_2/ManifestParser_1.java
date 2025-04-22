		if (defaultRevision == null && defaultRemote != null) {
			Remote remote = remotes.get(defaultRemote);
			if (remote != null) {
				defaultRevision = remote.revision;
			}
		}
