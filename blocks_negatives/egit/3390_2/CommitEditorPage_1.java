		if (committer != null && !committer.equals(author)) {
			Text committerText = toolkit.createText(userArea, MessageFormat
					.format(UIText.CommitEditorPage_LabelCommitter,
							committer.getName(), committer.getWhen()));
			committerText.setFont(parent.getFont());
			GridDataFactory.fillDefaults().span(1, 1).applyTo(committerText);
		}
