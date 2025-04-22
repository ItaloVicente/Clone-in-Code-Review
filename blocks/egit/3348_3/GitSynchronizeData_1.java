		return ancestorRevCommit;
	}

	private String extractRemoteName(String rev) {
		if (rev.contains(R_REMOTES)) {
			String remote = rev.replaceAll(R_REMOTES, ""); //$NON-NLS-1$
			return remote.substring(0, remote.indexOf("/")); //$NON-NLS-1$
		} else
			return null;
