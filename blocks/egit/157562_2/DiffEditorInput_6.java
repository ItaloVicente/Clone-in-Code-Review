		if (title == null) {
			if (base == null) {
				title = MessageFormat.format(UIText.DiffEditorInput_Title1,
						Utils.getShortObjectId(tip.getObjectId()),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			} else {
				title = MessageFormat.format(UIText.DiffEditorInput_Title2,
						Utils.getShortObjectId(base.getObjectId()),
						Utils.getShortObjectId(tip.getObjectId()),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			}
		}
