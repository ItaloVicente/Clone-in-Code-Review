	public String getAuthor() {
		return stagingView.bot().textWithLabel(UIText.StagingView_Author)
				.getText();
	}

	public String getCommitter() {
		return stagingView.bot().textWithLabel(UIText.StagingView_Committer)
				.getText();
	}

