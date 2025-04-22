	protected void setGitChangeSetPresentationModel() throws Exception {
		String modelName = util.getPluginLocalizedValue("ChangeSetModel.name");
		setPresentationModel(modelName, "Show " + modelName);
	}

	protected SWTBot setPresentationModel(String model) throws Exception {
		SWTBotView syncView = bot.viewByTitle("Synchronize");
		SWTBotToolbarDropDownButton dropDown = syncView
				.toolbarDropDownButton("Show File System Resources");
		dropDown.menuItem(model).click();
		dropDown.pressShortcut(KeyStroke.getInstance("ESC"));

		return syncView.bot();
	}

	protected SWTBot setPresentationModel(String modelName,
			String toolbarDropDownTooltip) throws Exception {
		SWTBotView syncView = bot.viewByTitle("Synchronize");
		for (SWTBotToolbarButton button : syncView.getToolbarButtons())
			if (button.getToolTipText().equals(toolbarDropDownTooltip)) {
				SWTBotToolbarDropDownButton dropDown = (SWTBotToolbarDropDownButton) button;
				dropDown.menuItem(modelName).click();
				dropDown.pressShortcut(KeyStroke.getInstance("ESC"));

			}

		return syncView.bot();
