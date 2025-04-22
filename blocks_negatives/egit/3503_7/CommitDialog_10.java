	private void updateFileSectionText() {
		filesSection.setText(MessageFormat.format(UIText.CommitDialog_Files,
				Integer.valueOf(filesViewer.getCheckedElements().length),
				Integer.valueOf(filesViewer.getTable().getItemCount())));
	}

	/**
	 * @return the calculated commit message
	 */
	private String calculateCommitMessage() {
		if(commitMessage != null) {
			return commitMessage;
		}

		if (amending)
			return previousCommitMessage;
		String calculatedCommitMessage = null;

		Set<IResource> resources = new HashSet<IResource>();
