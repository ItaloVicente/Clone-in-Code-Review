
			if (createBranch) {
				Git git = new Git(repo);
				CreateBranchCommand command = git.branchCreate();
				command.setName(name);
				command.setStartPoint(getStartPoint().name());
				if (upstreamMode != null)
					command.setUpstreamMode(upstreamMode);
				command.call();
