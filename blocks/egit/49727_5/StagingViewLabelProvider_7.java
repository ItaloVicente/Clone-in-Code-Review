	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			return getImage(element);
		} else {
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return getText(element);
		case 1:
			try {
				if (element instanceof StagingFolderEntry) {
					return null;
				}
				StagingEntry stagingEntry = (StagingEntry) element;
				Date modified;

				if (isStaged) {
					if (dirCache == null) {
						return null;
					}
					DirCacheEntry dirCacheEntry = dirCache
							.getEntry(stagingEntry.getPath());
					if (dirCacheEntry != null) {
						long lastModified = dirCacheEntry.getLastModified();
						if (lastModified == -1L) {
							return null;
						}
						modified = new Date(lastModified);
					} else {
						return null;
					}
				} else {
					File file = new File(stagingEntry.getRepository()
							.getWorkTree(), stagingEntry.getPath());
					long lastModified = file.lastModified();
					if (lastModified == -1L) {
						return null;
					}
					modified = new Date(lastModified);
				}

				if (showRelativeDate) {
					return RelativeDateFormatter.format(modified);
				} else {
					return absoluteFormatter.format(modified);
				}
			} catch (Exception e) {
				Activator.error(e.getCause().getMessage(), e.getCause());
				return null;
			}
		default:
			return null;
		}
	}

	public void setShowRelativeDate(boolean showRelativeDate) {
		this.showRelativeDate = showRelativeDate;
	}

	public void reloadDirCache(Repository currentRepository) {
		if (currentRepository == null){
			dirCache = null;
			return;
		}
		dirCache = new DirCache(currentRepository.getIndexFile(), currentRepository.getFS());
		try {
			Repository repository = stagingView.getCurrentRepository();
			dirCache = new DirCache(repository.getIndexFile(),
					repository.getFS());
			dirCache.read();
		} catch (Exception e) {
			if (e instanceof NoWorkTreeException) {
			} else {
				Activator.error(e.getMessage(), e);
			}
			dirCache = null;
		}
	}

