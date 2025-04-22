
	boolean doExecute(GitFlowRepository gfRepo, Shell activeShell) throws ExecutionException {
		Repository repository = gfRepo.getRepository();
		if (!gfRepo.hasBranches()) {
			boolean createMaster = openQuestion(
					activeShell,
					UIText.InitHandler_emptyRepository,
					UIText.InitHandler_doYouWantToInitNow);
			if (!createMaster) {
				return false;
			}

			createInitialCommit(repository);
		}

		InitDialog dialog = new InitDialog(activeShell, gfRepo, getBranches(repository));
		if (dialog.open() != Window.OK) {
			return false;
		}

		InitOperation initOperation = new InitOperation(repository,
				dialog.getResult());
		JobUtil.scheduleUserWorkspaceJob(initOperation,
				UIText.InitHandler_initializing, JobFamilies.GITFLOW_FAMILY);

		return true;
	}
