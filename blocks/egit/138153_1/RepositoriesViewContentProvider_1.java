		initToggleStateListener(srv, ToggleBranchHierarchyCommand.ID,
				branchHierarchyMode);
		initToggleStateListener(srv,
				ToggleRepositoryViewFilterCommand.TOGGLE_TAGS_ID, showTags);
		initToggleStateListener(srv,
				ToggleRepositoryViewFilterCommand.TOGGLE_REFS_ID, showRefs);
		initToggleStateListener(srv,
				ToggleRepositoryViewFilterCommand.TOGGLE_REMOTES_ID,
				showRemotes);
		initToggleStateListener(srv,
				ToggleRepositoryViewFilterCommand.TOGGLE_WORKTREE_ID,
				showWorkingTree);
		initToggleStateListener(srv,
				ToggleRepositoryViewFilterCommand.TOGGLE_STASHES_ID,
				showStashes);
		initToggleStateListener(srv,
				ToggleRepositoryViewFilterCommand.TOGGLE_SUBMODULES_ID,
				showSubmudules);
	}

	private void initToggleStateListener(ICommandService service,
			String commandId, final AtomicBoolean valueToUpdate) {
		State togglestate = service.getCommand(commandId)
