				Set<StagingEntry.Action> availableActions = getAvailableActions(
						fileSelection);

				boolean addReplaceWithFileInGitIndex = availableActions
						.contains(
								StagingEntry.Action.REPLACE_WITH_FILE_IN_GIT_INDEX);
				boolean addReplaceWithHeadRevision = availableActions.contains(
						StagingEntry.Action.REPLACE_WITH_HEAD_REVISION);
				boolean addStage = availableActions
						.contains(StagingEntry.Action.STAGE);
				boolean addUnstage = availableActions
						.contains(StagingEntry.Action.UNSTAGE);
				boolean addDelete = availableActions
						.contains(StagingEntry.Action.DELETE);
				boolean addIgnore = availableActions
						.contains(StagingEntry.Action.IGNORE);
				boolean addLaunchMergeTool = availableActions
						.contains(StagingEntry.Action.LAUNCH_MERGE_TOOL);
