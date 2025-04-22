			String currentBranchName;
			try {
				currentBranchName = repo.getBranch();
			} catch (IOException e) {
				Activator.handleError(UIText.TagAction_cannotGetBranchName, e,
						true);
				return null;
			}
