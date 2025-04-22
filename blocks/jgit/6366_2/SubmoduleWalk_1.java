		try {
			generator.setTree(iterator);
			PathFilter filter = PathFilter.create(path);
			generator.setFilter(filter);
			while (generator.next())
				if (filter.isDone(generator.walk))
					return generator;
		} catch (IOException e) {
			generator.release();
			throw e;
		}
		generator.release();
