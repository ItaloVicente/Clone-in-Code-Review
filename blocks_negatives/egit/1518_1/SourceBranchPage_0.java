		if (isSourceRepoEmpty()) {
			setMessage(UIText.SourceBranchPage_repoEmpty, IMessageProvider.WARNING);
			setPageComplete(true);
			return;
		}

