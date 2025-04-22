		tagLabelArea = toolkit.createComposite(tagArea);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tagLabelArea);
		GridLayoutFactory.fillDefaults().spacing(1, 1).applyTo(tagLabelArea);
	}

	private void fillDiffs(FileDiff[] diffs) {
		diffViewer.setInput(diffs);
		diffSection.setText(MessageFormat.format(
				UIText.CommitEditorPage_SectionFiles,
				Integer.valueOf(diffs.length)));
