	private void setPerson(Text text, PersonIdent person, boolean isAuthor) {
		PreferenceBasedDateFormatter formatter = PreferenceBasedDateFormatter
				.create();
		boolean isRelative = formatter
				.getFormat() == GitDateFormatter.Format.RELATIVE;
		String textTemplate = null;
		if (isAuthor) {
			textTemplate = isRelative
					? UIText.CommitEditorPage_LabelAuthorRelative
					: UIText.CommitEditorPage_LabelAuthor;
		} else {
			textTemplate = isRelative
					? UIText.CommitEditorPage_LabelCommitterRelative
					: UIText.CommitEditorPage_LabelCommitter;
		}
		text.setText(MessageFormat.format(textTemplate, person.getName(),
				person.getEmailAddress(), formatter.formatDate(person)));
	}

