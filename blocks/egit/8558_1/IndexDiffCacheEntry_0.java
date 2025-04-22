		if(!repository.isBare()) {
			try {
				lastIndex = DirCache.read(repository);
			} catch(IOException ex) {
				Activator.error(String.format(CoreText.IndexDiffCacheEntry_failed_index_load, repository), ex);
			}
		}
