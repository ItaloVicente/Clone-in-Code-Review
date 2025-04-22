		if (repository == null) {
			if (currentRepository == null)
				return;
			saveCommitMessageComponentState();
			currentRepository = null;
			StagingViewUpdate update = new StagingViewUpdate(null, null, null);
			unstagedTableViewer.setInput(update);
			stagedTableViewer.setInput(update);
			enableCommitWidgets(false);
			updateSectionText();
			form.setText(UIText.StagingView_NoSelectionTitle);
			return;
		}

		if (repository.isBare())
			return;

