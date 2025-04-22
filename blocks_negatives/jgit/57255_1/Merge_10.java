		Git git = new Git(db);
		MergeCommand mergeCmd = git.merge().setStrategy(mergeStrategy)
				.setSquash(squash).setFastForward(ff).setCommit(!noCommit);
		if (srcRef != null)
			mergeCmd.include(srcRef);
		else
			mergeCmd.include(src);
