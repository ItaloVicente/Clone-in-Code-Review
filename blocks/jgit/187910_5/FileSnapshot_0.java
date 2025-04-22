
	private void lazyLoadFileStoreAttributeCache() {
		if (fileStoreAttributeCache == null) {
			fileStoreAttributeCache = useConfig
					? FS.getFileStoreAttributes(file.toPath().getParent())
					: FALLBACK_FILESTORE_ATTRIBUTES;
		}
	}
