		if (!repository.isBare()) {
			try {
				lastIndex = repository.readDirCache();
			} catch(IOException ex) {
				Activator.error(String.format(CoreText.IndexDiffCacheEntry_errorCalculatingIndexDelta, repository), ex);
			}
		}
