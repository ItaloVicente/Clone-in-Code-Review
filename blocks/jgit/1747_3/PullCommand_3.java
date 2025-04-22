		if (!remote.equals(".")) {
			Ref r = null;
			if (fetchRes != null) {
				r = fetchRes.getAdvertisedRef(remoteBranchName);
				if (r == null)
					r = fetchRes.getAdvertisedRef(Constants.R_HEADS
							+ remoteBranchName);
			}
			if (r == null)
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().couldNotGetAdvertisedRef
			else
				commitToMerge = r.getObjectId();
		} else {
