		Text userText = new Text(userArea, SWT.FLAT | SWT.READ_ONLY);
		userText.setText(MessageFormat.format(
				author ? UIText.CommitEditorPage_LabelAuthor
						: UIText.CommitEditorPage_LabelCommitter, person
						.getName(), person.getEmailAddress(), person.getWhen()));
		toolkit.adapt(userText, false, false);
		userText.setData(FormToolkit.KEY_DRAW_BORDER, Boolean.FALSE);
