		if (!repository.isBare()) {
			try {
				lastIndex = repository.readDirCache();
			} catch (IOException ex) {
				Activator
						.error(MessageFormat
								.format(CoreText.IndexDiffCacheEntry_errorCalculatingIndexDelta,
										repository), ex);
			}
		}
