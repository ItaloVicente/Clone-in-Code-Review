		String master = repository.getConfig().getMaster();
		mergeResult = mergeTo(monitor, releaseBranchName, master);
		if (!mergeResult.getMergeStatus().isSuccessful()) {
			return;
		}

