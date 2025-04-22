			db = null;
			walk = null;
		} else {
			db = rm.getRepository();
			walk = new KidWalk(db);
			gitPath = rm.getRepoRelativePath(resource);
			walk.setTreeFilter(AndTreeFilter.create(PathFilterGroup
					.createFromStrings(Collections.singleton(gitPath)),
					TreeFilter.ANY_DIFF));
