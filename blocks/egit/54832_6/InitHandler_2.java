
		Repository repository = gfRepo.getRepository();
		if (!gfRepo.hasBranches()) {
			boolean createMaster = openQuestion(
					activeShell,
					UIText.InitHandler_emptyRepository,
					UIText.InitHandler_doYouWantToInitNow);
			if (!createMaster) {
				return null;
			}

			createInitialCommit(repository);
		}

		InitDialog dialog = new InitDialog(activeShell, gfRepo, getBranches(repository));
