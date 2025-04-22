	@Override
	public void dispose() {
		resourceManager.dispose();
	}

	private void setSourceRef(String refName) {
		String shortName = Repository.shortenRefName(refName);
		sourceNameLabel.setText(shortName);
		if (refName.startsWith(Constants.R_HEADS)
				|| refName.startsWith(Constants.R_REMOTES))
			sourceIcon.setImage(UIIcons.getImage(resourceManager,
					UIIcons.BRANCH));
		else if (refName.startsWith(Constants.R_TAGS))
			sourceIcon.setImage(UIIcons.getImage(resourceManager, UIIcons.TAG));
		else
			sourceIcon.setImage(UIIcons.getImage(resourceManager,
					UIIcons.CHANGESET));

		sourceRefName = refName;

		suggestBranchName(refName);
		upstreamConfig = UpstreamConfig.getDefault(myRepository, refName);
		checkPage();
	}

	private void setSourceCommit(RevCommit commit) {
		sourceNameLabel.setText(commit.abbreviate(7).name());
		sourceIcon.setImage(UIIcons
				.getImage(resourceManager, UIIcons.CHANGESET));

		sourceRefName = commit.name();

		upstreamConfig = UpstreamConfig.NONE;
		checkPage();
	}

	private void selectSource() {
		SourceSelectionDialog dialog = new SourceSelectionDialog(getShell(),
				myRepository, sourceRefName);
		int result = dialog.open();
		if (result == Window.OK) {
			String refName = dialog.getRefName();
			setSourceRef(refName);
			nameText.setFocus();
		}
	}

