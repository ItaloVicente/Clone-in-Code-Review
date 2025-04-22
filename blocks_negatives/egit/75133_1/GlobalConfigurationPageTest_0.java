		preferencePage = new Eclipse().openPreferencePage(preferencePage);
		SWTBotTreeItem team = preferencePage.bot().tree().getTreeItem("Team");
		team = TestUtil.expandAndWaitFor(team,
				util.getPluginLocalizedValue("GitPreferences_name"));
		team = TestUtil.expandAndWaitFor(team,
				util.getPluginLocalizedValue("ConfigurationPage.name"));
		team.select();
