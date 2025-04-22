			try (Git git = new Git(db)) {
				ListBranchCommand command = git.branchList();
				if (all)
					command.setListMode(ListMode.ALL);
				else if (remote)
					command.setListMode(ListMode.REMOTE);
