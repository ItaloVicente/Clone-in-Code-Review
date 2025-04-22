		try {
			PlotCommit commit = (PlotCommit) getSelection(event)
					.getFirstElement();
			ObjectId startAt = commit.getId();
			Repository repo = getRepository(event);
			String prompt = NLS.bind(
					UIText.CreateBranchHandler_CreatePromptMessage, startAt
							.name(), Constants.R_HEADS);
