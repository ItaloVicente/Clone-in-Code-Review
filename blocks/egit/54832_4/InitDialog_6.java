	@Override
	protected void okPressed() {
		String master = gitflowInitConfig.getMaster();
		Repository repository = gfRepo.getRepository();
		if (isMasterBranchAvailable(master, repository)) {
			super.okPressed();
			return;
		}
		boolean createBranch = openQuestion(getShell(),
				InitDialog_masterBranchIsMissing,
				NLS.bind(InitDialog_selectedMasterBranchDoesNotExistCreateNow, master));
		if (!createBranch) {
			return;
		}

		try {
			RevCommit head = gfRepo.findHead();
			new CreateLocalBranchOperation(repository, master, head).execute(null);
		} catch (CoreException | WrongGitFlowStateException e) {
			throw new RuntimeException(e);
		}
		super.okPressed();
	}

	private boolean isMasterBranchAvailable(String master, Repository repository) {
		try {
			return repository.getRef(R_HEADS + master) != null;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

