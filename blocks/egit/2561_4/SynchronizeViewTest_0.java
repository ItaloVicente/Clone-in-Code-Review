	@Test public void shouldExchangeCompareEditorSidesBetweenIncomingAndOutgoingChangesInGitChangeSet()
			throws Exception {
		String tagName = "exchangeCompareSidesInGitChangeSet";
		resetRepository(PROJ1);
		createTag(PROJ1, tagName);
		changeFilesInProject();
		commit(PROJ1);
		showDialog(PROJ1, "Team", "Synchronize...");

		launchSynchronization(SynchronizeWithAction_localRepoName, HEAD,
				SynchronizeWithAction_tagsName, tagName, false);
		SWTBot outgoingCompare = getCompareEditorForFileInGitChangeSet(FILE1,
				false);
		String outgoingLeft = outgoingCompare.styledText(0).getText();
		String outgoingRight = outgoingCompare.styledText(1).getText();

		showDialog(PROJ1, "Team", "Synchronize...");
		launchSynchronization(SynchronizeWithAction_tagsName, tagName,
				SynchronizeWithAction_localRepoName, HEAD, false);

		SWTBot incomingComp = getCompareEditorForFileInGitChangeSet(FILE1,
				false);
		assertThat(outgoingLeft, equalTo(incomingComp.styledText(1).getText()));
		assertThat(outgoingRight, equalTo(incomingComp.styledText(0).getText()));
	}

	@Test public void shouldExchangeCompareEditorSidesBetweenIncomingAndOutgoingChangesInWorkspaceModel()
			throws Exception {
		String tagName = "exchangeCompareSidesInWorkspace";
		resetRepository(PROJ1);
		createTag(PROJ1, tagName);
		changeFilesInProject();
		commit(PROJ1);
		showDialog(PROJ1, "Team", "Synchronize...");

		launchSynchronization(SynchronizeWithAction_localRepoName, HEAD,
				SynchronizeWithAction_tagsName, tagName, false);
		SWTBotEditor compEditor = getCompareEditorForFileInWorkspaceModel(false);
		SWTBot outgoingCompare = compEditor.bot();
		String outgoingLeft = outgoingCompare.styledText(0).getText();
		String outgoingRight = outgoingCompare.styledText(1).getText();

		showDialog(PROJ1, "Team", "Synchronize...");
		launchSynchronization(SynchronizeWithAction_tagsName, tagName,
				SynchronizeWithAction_localRepoName, HEAD, false);

		SWTBot incomingComp = getCompareEditorForFileInWorkspaceModel(false)
				.bot();
		String incomingLeft = incomingComp.styledText(0).getText();
		String incomingRight = incomingComp.styledText(1).getText();
		assertThat(outgoingLeft, equalTo(incomingRight));
		assertThat(outgoingRight, equalTo(incomingLeft));
	}

