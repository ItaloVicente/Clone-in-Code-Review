		mergeResult = mergeTo(monitor, hotfixBranchName,
				repository.getConfig().getMaster());
		if (!mergeResult.getMergeStatus().isSuccessful()) {
			throw new CoreException(
					error(CoreText.HotfixFinishOperation_mergeFromHotfixToMasterFailed));
		}

		mergeResult = finish(monitor, hotfixBranchName);
