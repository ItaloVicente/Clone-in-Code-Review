		diffSection.setText(getIndexDiffSectionTitle(Integer.valueOf(diffs.length)));
	}

	String getIndexDiffSectionTitle(Integer numChanges) {
		return MessageFormat.format(UIText.CommitEditorPage_SectionFiles,
				numChanges);
