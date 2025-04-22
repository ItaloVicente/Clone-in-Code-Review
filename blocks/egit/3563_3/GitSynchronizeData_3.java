			String remoteWithBranchName = rev.replaceAll(R_REMOTES, ""); //$NON-NLS-1$
			int firstSeparator = remoteWithBranchName.indexOf("/"); //$NON-NLS-1$

			String remote = remoteWithBranchName.substring(0, firstSeparator);
			String name = remoteWithBranchName.substring(firstSeparator,
					remoteWithBranchName.length());

			return new RemoteConfig(remote, name);
