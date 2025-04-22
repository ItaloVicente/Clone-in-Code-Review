		useTemplate = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_MESSAGE_TEMPLATE,
				UIText.CommittingPreferencePage_useMessageTemplate,
				formattingGroup);
		useTemplate.getDescriptionControl(formattingGroup).setToolTipText(
				UIText.CommittingPreferencePage_useMessageTemplateTooltip);
		addField(useTemplate);

		showComments = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_MESSAGE_TEMPLATE_COMMENTS,
				UIText.CommittingPreferencePage_useMessageTemplateShowComments,
				formattingGroup);
		showComments.getDescriptionControl(formattingGroup).setToolTipText(
				UIText.CommittingPreferencePage_useMessageTemplateShowCommentsTooltip);
		GridDataFactory.fillDefaults().indent(UIUtils.getControlIndent(), 0)
				.applyTo(showComments.getDescriptionControl(formattingGroup));
		addField(showComments);
		showComments.setEnabled(
				getPreferenceStore().getBoolean(
						UIPreferences.COMMIT_DIALOG_MESSAGE_TEMPLATE),
				formattingGroup);

