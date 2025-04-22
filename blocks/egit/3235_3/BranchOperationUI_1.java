			CreateBranchWizard wiz;
			try {
				wiz = new CreateBranchWizard(repository, repository.getFullBranch());
			} catch (IOException e) {
				wiz = new CreateBranchWizard(repository);
			}
