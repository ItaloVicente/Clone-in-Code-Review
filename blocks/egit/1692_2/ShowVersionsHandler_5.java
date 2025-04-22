			StringBuilder commitList = new StringBuilder();
			for (ObjectId commitId : ids) {
				try {
				Repository repo = getRepository(event);
				RevCommit commit = new RevWalk(repo).parseCommit(commitId);
				commitList.append("\n"); //$NON-NLS-1$
				commitList.append(commit.getShortMessage());
				commitList.append(' ');
				commitList.append('[');
				commitList.append(commit.name());
				commitList.append(']');
				} catch (IOException e) {
					errorOccured = true;
				}
