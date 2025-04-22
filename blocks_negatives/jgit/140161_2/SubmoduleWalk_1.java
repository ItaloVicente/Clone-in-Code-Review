		SubmoduleWalk generator = new SubmoduleWalk(repository);
		try {
			generator.setTree(iterator);
			PathFilter filter = PathFilter.create(path);
			generator.setFilter(filter);
			generator.setRootTree(iterator);
			while (generator.next())
				if (filter.isDone(generator.walk))
					return generator;
		} catch (IOException e) {
			generator.close();
			throw e;
