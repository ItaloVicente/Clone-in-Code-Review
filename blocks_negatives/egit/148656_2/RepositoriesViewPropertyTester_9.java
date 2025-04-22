			try {
				String branch = repository.getFullBranch();
				if (branch == null) {
				}
				return branch.startsWith(Constants.R_HEADS);
			} catch (IOException e) {
				return false;
