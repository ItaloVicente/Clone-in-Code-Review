		if (commitsToUse != null) {
			while (commitsToUse.hasNext()) {
				RevCommit commit = commitsToUse.next();
				if (preserveMerges || commit.getParentCount() == 1)
					cherryPickList.add(commit);
			}
