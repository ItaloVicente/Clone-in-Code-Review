			ITypedElement next;
			try {
				Ref head = repository.getRef(Constants.HEAD);
				RevWalk rw = new RevWalk(repository);
				RevCommit commit = rw.parseCommit(head.getObjectId());
				rw.markStart(commit);
				rw.setTreeFilter(AndTreeFilter.create(
						PathFilter.create(gitPath), TreeFilter.ANY_DIFF));
				RevCommit latestFileCommit = rw.next();
				if (latestFileCommit == null)
					latestFileCommit = commit;

				next = CompareUtils.getFileRevisionTypedElement(gitPath,
						latestFileCommit, repository);
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
