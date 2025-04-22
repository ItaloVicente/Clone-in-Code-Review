				children = NO_CHILDREN;
				return;
			}
			try (RevWalk walk = new RevWalk(repo)) {
				walk.setRetainBody(true);
				walk.markStart(walk.parseCommit(update.getNewObjectId()));
				walk.markUninteresting(
						walk.parseCommit(update.getOldObjectId()));
				List<CommitAdapter> commits = new ArrayList<>();
				for (RevCommit commit : walk) {
					if (monitor.isCanceled()) {
						break;
					}
					commits.add(new CommitAdapter(repo, commit));
				}
				children = commits.toArray();
				collector.add(children, monitor);
			} catch (IOException e) {
				Activator.logError("Error parsing commits from fetch result", //$NON-NLS-1$
						e);
				children = NO_CHILDREN;
