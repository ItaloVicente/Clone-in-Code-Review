		boolean mergeToolAvailable = true;
		final CheckResult checkResult;
		if (!conflictListFailure) {
			checkResult = FileChecker.checkFiles(repo, conflictPaths);
			mergeToolAvailable = checkResult.isOk();
		}
		else {
			checkResult = null;
			mergeToolAvailable = false;
		}

