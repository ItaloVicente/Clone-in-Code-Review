		if (object instanceof GitModelCommit)
			return new GitCommitMapping((GitModelCommit) object);
		if (object instanceof GitModelCache)
			return new GitCommitMapping((GitModelCache) object);
		if (object instanceof GitModelWorkingTree)
			return new GitCommitMapping((GitModelWorkingTree) object);
