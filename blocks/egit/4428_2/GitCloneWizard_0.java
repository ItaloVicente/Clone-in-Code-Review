		if (gerritConfiguration.configureGerrit()) {
			boolean hasReviewNotes = hasReviewNotes(uri, timeout, credentials);
			if (!hasReviewNotes)
				MessageDialog.openInformation(getShell(),
						UIText.GitCloneWizard_MissingNotesTitle,
						UIText.GitCloneWizard_MissingNotesMessage);
			doGerritConfiguration(remoteName, op, hasReviewNotes);
		}

