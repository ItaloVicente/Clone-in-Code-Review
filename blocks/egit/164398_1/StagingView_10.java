
		final Repository repo = currentRepository;
		commitAndPushButton.setEnabled(
				repo != null && (commitEnabled || canPushHeadOnly())
						&& !repo.getRepositoryState().isRebasing());
		PushMode pushMode = getPushMode();
		commitAndPushButton.setImage(getImage(
				pushMode != null && pushMode == PushMode.GERRIT ? UIIcons.GERRIT
						: UIIcons.PUSH));
		commitAndPushButton
				.setText(canPushHeadOnly() ? UIText.StagingView_PushHEAD
						: UIText.StagingView_CommitAndPush);
		commitAndPushButton.requestLayout();
