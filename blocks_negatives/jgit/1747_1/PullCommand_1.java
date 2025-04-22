		Ref r = fetchRes.getAdvertisedRef(remoteBranchName);
		if (r == null)
			r = fetchRes.getAdvertisedRef(Constants.R_HEADS + remoteBranchName);
		if (r == null) {
			String remoteTrackingBranch = Constants.R_REMOTES + remote + '/'
					+ branchName;
