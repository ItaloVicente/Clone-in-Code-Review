	public boolean isSaveAsAllowed() {
		return false;
	}

	public void doSaveAs() {
		throw new UnsupportedOperationException();
	}

	public void doSave(IProgressMonitor monitor) {
		commit(false);
	}

	public boolean isSaveOnCloseNeeded() {
		return true;
	}

	public boolean isDirty() {
		if (commitPending)
			return false;
		if (form == null || form.isDisposed())
			return false;
		if (amendPreviousCommitAction != null
				&& amendPreviousCommitAction.isEnabled()
				&& amendPreviousCommitAction.isChecked()) {
			CommitInfo headCommitInfo = CommitHelper
					.getHeadCommitInfo(currentRepository);
			if (headCommitInfo != null) {
				String commitMessage = commitMessageText.getText();
				if (!commitMessage.equals(headCommitInfo.getCommitMessage()))
					return true;
			}
		} else {
			if (userEnteredCommmitMessage())
				return true;
		}
		return false;
	}

