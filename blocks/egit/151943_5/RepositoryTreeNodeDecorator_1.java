		if (numberOfBranches == 1) {
			decorated = true;
			String repoLabel = singleRepoName != null ? singleRepoName
					: MULTIPLE_REPOSITORIES;
			decoration.addSuffix(
					OPEN_BRACKET + repoLabel + ' ' + singleBranch + ']');
		} else if (numberOfDirectories > 1) {
			decorated = true;
			decoration.addSuffix(OPEN_PARENTHESIS + numberOfDirectories + ')');
