
		return true;
	}

	private boolean rawTextMatches(RawText resourceContent,
			RawText repositoryContent) {
		for (int i = 0; i < repositoryContent.size(); i++) {
			if (!repositoryContent.equals(i, resourceContent, i)) {
				return false;
			}
		}
		return true;
