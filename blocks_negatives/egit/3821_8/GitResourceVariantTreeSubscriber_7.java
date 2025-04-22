
		String path = stripWorkDir(repo.getWorkTree(), res.getLocation()
				.toFile());

		TreeWalk tw = new TreeWalk(repo);
		if (path.length() > 0)
			tw.setFilter(PathFilter.create(path));
