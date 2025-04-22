		generator.setTree(treeId);
		PathFilter filter = PathFilter.create(path);
		generator.setFilter(filter);
		while (generator.next())
			if (filter.isDone(generator.walk))
				return generator;
