			if (r == null)
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().couldNotGetAdvertisedRef, remoteBranchName));
			else
				commitToMerge = r.getObjectId();
		} else {
