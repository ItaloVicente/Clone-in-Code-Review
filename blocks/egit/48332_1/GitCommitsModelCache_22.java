		try (final RevWalk rw = new RevWalk(repo)) {

			final RevFlag localFlag = rw.newFlag("local"); //$NON-NLS-1$
			final RevFlag remoteFlag = rw.newFlag("remote"); //$NON-NLS-1$
			final RevFlagSet allFlags = new RevFlagSet();
			allFlags.add(localFlag);
			allFlags.add(remoteFlag);
			rw.carry(allFlags);

			RevCommit srcCommit = rw.parseCommit(srcId);
			srcCommit.add(localFlag);
			rw.markStart(srcCommit);
			srcCommit = null; // free not needed resources

			RevCommit dstCommit = rw.parseCommit(dstId);
			dstCommit.add(remoteFlag);
			rw.markStart(dstCommit);
			dstCommit = null; // free not needed resources

			if (pathFilter != null)
				rw.setTreeFilter(pathFilter);

			List<Commit> result = new ArrayList<Commit>();
			for (RevCommit revCommit : rw) {
				if (revCommit.hasAll(allFlags))
					break;

				Commit commit = new Commit();
				commit.shortMessage = revCommit.getShortMessage();
				commit.commitId = AbbreviatedObjectId.fromObjectId(revCommit);
				commit.authorName = revCommit.getAuthorIdent().getName();
				commit.committerName = revCommit.getCommitterIdent().getName();
				commit.commitDate = revCommit.getAuthorIdent().getWhen();

				RevCommit parentCommit = getParentCommit(revCommit);
				if (revCommit.has(localFlag))
					commit.direction = RIGHT;
				else if (revCommit.has(remoteFlag))
					commit.direction = LEFT;
				else
					throw new GitCommitsModelDirectionException();

				commit.children = getChangedObjects(repo, revCommit,
						parentCommit, pathFilter, commit.direction);

				if (commit.children != null)
					result.add(commit);
			}
			rw.dispose();
			return result;
