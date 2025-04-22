		MergeCommand mergeCmd = git.merge().setStrategy(mergeStrategy)
				.setSquash(squash).setFastForward(ff);
		if (srcRef != null)
			mergeCmd.include(srcRef);
		else
			mergeCmd.include(src);
		MergeResult result = mergeCmd.call();
