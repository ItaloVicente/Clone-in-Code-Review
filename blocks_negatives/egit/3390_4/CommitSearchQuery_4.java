			RevCommit commit = walk.parseCommit(repository
					.resolve(Constants.HEAD));
			if (commit != null) {
				walk.markStart(commit);
				commit = walk.next();
				while (commit != null) {
