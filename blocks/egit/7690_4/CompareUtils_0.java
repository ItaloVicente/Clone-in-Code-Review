
			RevCommit latestFileCommit;
			RevWalk rw = new RevWalk(repository);
			try {
				RevCommit headCommit = rw.parseCommit(head.getObjectId());
				rw.markStart(headCommit);
				rw.setTreeFilter(AndTreeFilter.create(
						PathFilter.create(repoRelativePath),
						TreeFilter.ANY_DIFF));
				latestFileCommit = rw.next();
				if (latestFileCommit == null)
					latestFileCommit = headCommit;
			} finally {
				rw.release();
			}

			return CompareUtils.getFileRevisionTypedElement(repoRelativePath, latestFileCommit, repository);
