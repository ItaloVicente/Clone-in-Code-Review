	private RemoteConfig extractRemoteName(String rev) {
		if (rev.contains(R_REMOTES)) {
			String remoteWithBranchName = rev.replaceAll(R_REMOTES, ""); //$NON-NLS-1$

			String remote = remoteWithBranchName.substring(0, firstSeparator);
			String name = remoteWithBranchName.substring(firstSeparator + 1,
					remoteWithBranchName.length());

			return new RemoteConfig(remote, R_HEADS + name);
		} else {
			String realName;
			Ref ref;
			try {
				ref = repo.getRef(rev);
			} catch (IOException e) {
				ref = null;
