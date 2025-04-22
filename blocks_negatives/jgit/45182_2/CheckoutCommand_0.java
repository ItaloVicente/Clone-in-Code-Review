				Git git = new Git(repo);
				CreateBranchCommand command = git.branchCreate();
				command.setName(name);
				if (startCommit != null)
					command.setStartPoint(startCommit);
				else
					command.setStartPoint(startPoint);
				if (upstreamMode != null)
					command.setUpstreamMode(upstreamMode);
				command.call();
