		final KidWalk w = new KidWalk(rm.getRepository());
		gitPath = rm.getRepoRelativePath(resource);
		w.setTreeFilter(AndTreeFilter.create(PathFilterGroup
				.createFromStrings(Collections.singleton(gitPath)),
				TreeFilter.ANY_DIFF));
		return w;
