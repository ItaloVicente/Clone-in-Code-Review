			List<RevCommit> commits = new LinkedList<RevCommit>();
			if (this.settings.isAllBranches()) {
				for (Ref ref : repository.getRefDatabase()
						.getRefs(Constants.R_HEADS).values())
					if (!ref.isSymbolic())
						commits.add(walk.parseCommit(ref.getObjectId()));
				for (Ref ref : repository.getRefDatabase()
						.getRefs(Constants.R_REMOTES).values())
					if (!ref.isSymbolic())
						commits.add(walk.parseCommit(ref.getObjectId()));
			} else {
				ObjectId headCommit = repository.resolve(Constants.HEAD);
				if (headCommit != null)
					commits.add(walk.parseCommit(headCommit));
			}

			if (!commits.isEmpty()) {
				walk.markStart(commits);
				for (RevCommit commit : walk) {
