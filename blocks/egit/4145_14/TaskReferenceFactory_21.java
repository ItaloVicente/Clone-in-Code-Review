			if (!(modelCommit.getParent() instanceof GitModelRepository))
				return null; // should never happen

			GitModelRepository parent = (GitModelRepository) modelCommit.getParent();
			Repository repo = parent.getRepository();
			AbbreviatedObjectId id = modelCommit.getCachedCommitObj().getId();

			commit = new RevWalk(repo).lookupCommit(id.toObjectId());
