
		String unstagedSectionText = ""; //$NON-NLS-1$
		if (currentRepository == null) {
			unstagedSectionText = UIText.StagingView_NoSelectionTitle;
		} else if (repository != null) {
			unstagedSectionText = GitLabels.getStyledLabelSafe(repository)
					.toString();
		}
