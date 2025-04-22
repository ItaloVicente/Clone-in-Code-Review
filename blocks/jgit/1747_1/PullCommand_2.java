		if (!remote.equals(".")) {
			Ref r = null;
			if (fetchRes != null) {
				r = fetchRes.getAdvertisedRef(remoteBranchName);
				if (r == null)
					r = fetchRes.getAdvertisedRef(Constants.R_HEADS
							+ remoteBranchName);
			}
			if (r == null) {
				String remoteTrackingBranch = Constants.R_REMOTES + remote
						+ '/' + branchName;
				try {
					commitToMerge = repo.resolve(remoteTrackingBranch);
				} catch (IOException e) {
					throw new JGitInternalException(
							JGitText.get().exceptionCaughtDuringExecutionOfPullCommand
							e);
				}

			} else
				commitToMerge = r.getObjectId();
		} else {
