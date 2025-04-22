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
					loadDirCache();
					DirCacheEntry dirCacheEntry = dirCache
							.getEntry(stagingEntry.getPath());
					if (dirCacheEntry != null) {
						modified = new Date(dirCacheEntry.getLastModified());
					} else {
						modified = new Date(-1L);
					}
				} else {
					File file = new File(stagingEntry.getRepository()
							.getWorkTree(), stagingEntry.getPath());
					modified = new Date(file.lastModified());
				}

				if (showRelativeDate) {
					return RelativeDateFormatter.format(modified);
				}
				else {
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

	private void loadDirCache() {
		if (dirCache == null
				|| dirCacheRepo != stagingView.getCurrentRepository()) {
			try {
				Repository repository = stagingView.getCurrentRepository();
				dirCacheRepo = repository;
				dirCache = new DirCache(repository.getIndexFile(),
						repository.getFS());
				dirCache.read();
			} catch (Exception e) {
				Activator.error(e.getMessage(), e);
			}
		}
	}

	public void setShowRelativeDate(boolean showRelativeDate) {
		this.showRelativeDate = showRelativeDate;
	}

