		Text userText = toolkit
				.createText(userArea, MessageFormat.format(
						author ? UIText.CommitEditorPage_LabelAuthor
								: UIText.CommitEditorPage_LabelCommitter,
						person.getName(), person.getEmailAddress(), person
								.getWhen()));
		userText.setEditable(false);
