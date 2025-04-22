		try {
			String fullBranch = repository.getFullBranch();
			return fullBranch != null
					&& fullBranch.startsWith(Constants.R_HEADS);
		} catch (Exception e) {
		}

		return false;
