		String master = repository.getConfig().getMaster();
		mergeResult = mergeTo(monitor, releaseBranchName, master);
		if (!mergeResult.isSuccessful()) {
			return;
		}

