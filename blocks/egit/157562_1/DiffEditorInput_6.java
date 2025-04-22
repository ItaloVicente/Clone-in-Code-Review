		if (title == null) {
			if (base == null) {
				title = MessageFormat.format(UIText.DiffEditorInput_Title1,
						tip.getObjectId().abbreviate(7).name(),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			} else {
				title = MessageFormat.format(UIText.DiffEditorInput_Title2,
						base.getObjectId().abbreviate(7).name(),
						tip.getObjectId().abbreviate(7).name(),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			}
		}
