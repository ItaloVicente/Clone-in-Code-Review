		IWizard wiz = null;
		List<Ref> branches = getBranchesExcludingCurrent(page, repo);

		if (branches.isEmpty()) {
			PlotCommit commit = (PlotCommit) getSelection(page)
					.getFirstElement();
			wiz = new CreateBranchWizard(repo, commit.name());
		} else {
			Ref branch = branches.get(0);
			wiz = new CreateBranchWizard(repo, branch.getName());
		}

