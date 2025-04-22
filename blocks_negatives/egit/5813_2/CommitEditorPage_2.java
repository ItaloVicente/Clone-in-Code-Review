		FileDiff[] diffs = getCommit().getDiffs();
		viewer.setInput(diffs);
		files.setText(MessageFormat.format(
				UIText.CommitEditorPage_SectionFiles,
				Integer.valueOf(diffs.length)));

		updateSectionClient(files, filesArea, toolkit);
