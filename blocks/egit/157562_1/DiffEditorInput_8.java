		if (tooltip == null) {
			if (base == null) {
				tooltip = MessageFormat.format(UIText.DiffEditorInput_Tooltip1,
						tip.getObjectId().abbreviate(7).name(),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			} else {
				tooltip = MessageFormat.format(
						UIText.DiffEditorInput_Tooltip2,
						base.getObjectId().abbreviate(7).name(),
						tip.getObjectId().abbreviate(7).name(),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			}
		}
		return tooltip;
