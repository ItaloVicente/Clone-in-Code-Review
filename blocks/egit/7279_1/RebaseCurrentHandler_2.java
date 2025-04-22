	private Ref getRef(PlotCommit commit, Repository repository, String currentBranch) {
		int count = commit.getRefCount();
		if (count == 0)
			return new ObjectIdRef.Unpeeled(Storage.LOOSE, commit.getName(), commit);
		else if (count == 1)
			return commit.getRef(0);
		else {
			BranchConfig branchConfig = new BranchConfig(repository.getConfig(), currentBranch);
			String trackingBranch = branchConfig.getRemoteTrackingBranch();
			Ref remoteRef = null;

			for (int i = 0; i < count; i++) {
				Ref ref = commit.getRef(i);
				if (trackingBranch != null && trackingBranch.equals(ref.getName()))
					return ref;
				if (ref.getName().startsWith(Constants.R_REMOTES))
					remoteRef = ref;
			}

			if (remoteRef != null)
				return remoteRef;
			else
				return commit.getRef(0);
		}
	}

	private String getCurrentBranch(Repository repository) throws ExecutionException {
		try {
			return repository.getBranch();
		} catch (IOException e) {
			throw new ExecutionException(UIText.RebaseCurrentRefCommand_ErrorGettingCurrentBranchMessage, e);
		}
	}
