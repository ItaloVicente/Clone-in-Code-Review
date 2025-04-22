		srcRefCombo = new RemoteSelectionCombo(composite, syncRepos,
				UIText.RemoteSelectionCombo_sourceName,
				UIText.RemoteSelectionCombo_sourceRef);
		srcRefCombo.setDefaultValue(UIText.SynchronizeWithAction_localRepoName, HEAD);
		srcRefCombo.setLayoutData(data);
		srcRefCombo.setLayoutData(GridDataFactory.fillDefaults().grab(true,
				false).create());
		srcRefCombo.setEnabled(false);

