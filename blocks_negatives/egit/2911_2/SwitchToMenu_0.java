				RevCommit commit = null;
				Ref currentBranch = null;
				try {
					String branch = repository.getFullBranch();
					if (ObjectId.isId(branch)) {
						commit = new RevWalk(repository).parseCommit(ObjectId
								.fromString(branch));
					} else {
						currentBranch = repository.getRef(branch);
					}
				} catch (IOException e1) {
				}
				CreateBranchWizard wiz;
				if (currentBranch != null)
					wiz = new CreateBranchWizard(repository, currentBranch);
				else
					wiz = new CreateBranchWizard(repository, commit);
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				new WizardDialog(shell, wiz).open();
