		actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), refreshAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.BUILD_PROJECT.getId(), buildAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.OPEN_PROJECT.getId(), openProjectAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_PROJECT.getId(), closeProjectAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_UNRELATED_PROJECTS.getId(),
				closeUnrelatedProjectsAction);
	}

	@Override
