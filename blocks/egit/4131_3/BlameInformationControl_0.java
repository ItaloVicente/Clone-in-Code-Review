		String linkText = MessageFormat.format(
				UIText.BlameInformationControl_Commit, commit.name());
		commitLink.setText(linkText);
		StyleRange styleRange = new StyleRange(0, linkText.length(), null, null);
		styleRange.underline = true;
		commitLink.setStyleRange(styleRange);
