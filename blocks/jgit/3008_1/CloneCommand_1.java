		if (head.getName().startsWith(Constants.R_HEADS)) {
			final RefUpdate newHead = repo.updateRef(Constants.HEAD);
			newHead.disableRefLog();
			newHead.link(head.getName());
			addMergeConfig(repo
		}

