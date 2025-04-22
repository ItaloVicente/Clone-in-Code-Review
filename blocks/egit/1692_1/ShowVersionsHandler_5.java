			StringBuilder commitList = new StringBuilder();
			for (RevCommit commit : ids) {
				commitList.append("\n"); //$NON-NLS-1$
				commitList.append(commit.getShortMessage());
				commitList.append(' ');
				commitList.append('[');
				commitList.append(commit.name());
				commitList.append(']');
