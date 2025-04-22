		try {
			DirCache index = repository.readDirCache();
			generator.setTree(new DirCacheIterator(index));
			generator.setRootTree(new DirCacheIterator(index));
			generator.useWorkingTree = true;
		} catch (IOException e) {
			generator.release();
			throw e;
		}
