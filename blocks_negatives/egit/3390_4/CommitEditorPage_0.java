		if (author != null) {
			Text authorText = toolkit.createText(userArea, MessageFormat
					.format(UIText.CommitEditorPage_LabelAuthor,
							author.getName(), author.getWhen()));
			GridDataFactory.fillDefaults().span(1, 1).applyTo(authorText);
		}
