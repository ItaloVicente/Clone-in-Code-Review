		diffSection.setText(getDiffSectionTitle(Integer.valueOf(diffs.length)));
	}

	String getDiffSectionTitle(Integer numChanges) {
		return MessageFormat.format(UIText.CommitEditorPage_SectionFiles,
				numChanges);
