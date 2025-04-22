		for (Repository repo : repos)
			try {
				String fullBranch = repo.getFullBranch();
				if (fullBranch == null
						|| !fullBranch.startsWith(Constants.R_REFS)) {
					return false;
				}
				Ref head = repo.exactRef(Constants.HEAD);
				if (head == null || head.getObjectId() == null) {
					return false;
				}
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, false);
