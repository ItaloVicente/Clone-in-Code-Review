		if (tooltip == null) {
			if (base == null) {
				tooltip = MessageFormat.format(UIText.DiffEditorInput_Tooltip1,
						Utils.getShortObjectId(tip.getObjectId()),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			} else {
				tooltip = MessageFormat.format(
						UIText.DiffEditorInput_Tooltip2,
						Utils.getShortObjectId(base.getObjectId()),
						Utils.getShortObjectId(tip.getObjectId()),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			}
		}
		return tooltip;
