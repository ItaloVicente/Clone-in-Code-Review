			try (Git git = new Git(repo)) {
				if (otherParentsUnchanged) {
					boolean isMerge = commitToPick.getParentCount() > 1;
					String ourCommitName = getOurCommitName();
					CherryPickCommand pickCommand = git.cherryPick()
							.include(commitToPick)
							.setOurCommitName(ourCommitName)
							.setReflogPrefix(REFLOG_PREFIX)
							.setStrategy(strategy);
