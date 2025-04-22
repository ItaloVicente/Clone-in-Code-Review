		try {
			DirCache index = repository.readDirCache();
			generator.setTree(new DirCacheIterator(index));
			DirCacheIterator iterator = new DirCacheIterator(index);
			generator.readModulesConfig(iterator);
		} catch (IOException e) {
			generator.release();
			throw e;
		} catch (ConfigInvalidException e) {
			generator.release();
			throw new IOException(e);
    }
