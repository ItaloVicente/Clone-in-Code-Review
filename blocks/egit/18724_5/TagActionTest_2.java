	@Test
	public void testCreateTagAndStartPush() throws Exception {
		SWTBotShell tagDialog = openTagDialog();
		SWTBotButton button = tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagAndStartPushButton);
		assertFalse("'Create Tag And Start Push' should be disabled",
				button.isEnabled());
		tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
				.setText("tag-to-push");
		tagDialog.bot().styledTextWithLabel(UIText.CreateTagDialog_tagMessage)
				.setText("Tag to push");
		button.click();

		SWTBotShell pushTagsWizard = bot
				.shell(UIText.PushTagsWizard_WindowTitle);
		pushTagsWizard.close();
	}

