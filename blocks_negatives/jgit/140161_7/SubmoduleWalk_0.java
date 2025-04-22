		SubmoduleWalk generator = new SubmoduleWalk(repository);
		try {
			generator.setTree(treeId);
			PathFilter filter = PathFilter.create(path);
			generator.setFilter(filter);
			generator.setRootTree(treeId);
			while (generator.next())
				if (filter.isDone(generator.walk))
					return generator;
		} catch (IOException e) {
			generator.close();
			throw e;
