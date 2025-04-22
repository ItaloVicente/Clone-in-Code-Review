		int parentIndex = -1;
		if (commit.getParentCount() > 1) {
			List<RevCommit> parents = new ArrayList<>();
			String branch = null;
			try {
				for (RevCommit parent : commit.getParents()) {
					parents.add(repo.parseCommit(parent));
				}
				branch = repo.getBranch();
			} catch (Exception e) {
				Activator.handleError(e.getLocalizedMessage(), e, true);
				return null;
			}
			CommitSelectDialog selectCommit = new CommitSelectDialog(shell,
					parents, getLaunchMessage(repo));
			selectCommit.create();
			selectCommit.setTitle(UIText.CommitSelectDialog_ChooseParentTitle);
			selectCommit.setMessage(MessageFormat.format(
					UIText.CherryPickHandler_CherryPickMergeMessage,
					commit.abbreviate(7).name(), branch));
			if (selectCommit.open() != Window.OK) {
				return null;
			}
			parentIndex = parents.indexOf(selectCommit.getSelectedCommit());
		} else if (!confirmCherryPick(shell, repo, commit)) {
