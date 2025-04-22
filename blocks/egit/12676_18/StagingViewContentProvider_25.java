	public Object[] getChildren(Object parentElement) {
		if (repository == null)
			return new Object[0];
		if (parentElement instanceof StagingEntry)
			return new Object[0];
		if (parentElement instanceof StagingFolderEntry) {
			return getFolderChildren((StagingFolderEntry) parentElement);
		} else {
			if (stagingView.getPresentation() == Presentation.LIST)
				return content;
			else {
				StagingFolderEntry[] allFolders = getStagingFolderEntries();
				List<Object> roots = new ArrayList<Object>();
				for (StagingFolderEntry folder : allFolders)
					if (folder.getParentPath().segmentCount() == 0)
						roots.add(folder);
				for (StagingEntry entry : content)
					if (!entry.getPath().contains("/")) //$NON-NLS-1$
						roots.add(entry);
				return roots.toArray(new Object[roots.size()]);
			}
		}
	}

	private Object[] getFolderChildren(StagingFolderEntry parent) {
		IPath parentPath = parent.getPath();
		List<Object> children = new ArrayList<Object>();
		for (StagingFolderEntry folder : getStagingFolderEntries()) {
			if (folder.getParentPath().equals(parentPath)) {
				folder.setParent(parent);
				children.add(folder);
			}
		}
		for (StagingEntry file : content) {
			if (file.getParentPath().equals(parentPath)) {
				file.setParent(parent);
				children.add(file);
			}
		}
		return children.toArray(new Object[children.size()]);
	}

	StagingFolderEntry[] getStagingFolderEntries() {
		Presentation presentation = stagingView.getPresentation();
		switch (presentation) {
		case COMPACT_TREE:
			return getCompactTreeFolders();
		case TREE:
			return getTreeFolders();
		default:
			return new StagingFolderEntry[0];
		}
	}

	private StagingFolderEntry[] getCompactTreeFolders() {
		if (compactTreeFolders == null)
			compactTreeFolders = calculateTreeFolders(true);
		return compactTreeFolders;
	}

	private StagingFolderEntry[] getTreeFolders() {
		if (treeFolders == null)
			treeFolders = calculateTreeFolders(false);
		return treeFolders;
	}

	private StagingFolderEntry[] calculateTreeFolders(boolean compact) {
		if (content == null || content.length == 0)
			return new StagingFolderEntry[0];

		Set<IPath> folderPaths = new HashSet<IPath>();
		Map<IPath, String> childSegments = new HashMap<IPath, String>();

		for (StagingEntry file : content) {
			IPath folderPath = file.getParentPath();
			if (folderPath.segmentCount() == 0)
				continue;
			folderPaths.add(folderPath);
			for (IPath p = folderPath; p.segmentCount() != 1; p = p
					.removeLastSegments(1)) {
				IPath parent = p.removeLastSegments(1);
				if (!compact) {
					folderPaths.add(parent);
				} else {
					String childSegment = p.lastSegment();
					String knownChildSegment = childSegments.get(parent);
					if (knownChildSegment == null) {
						childSegments.put(parent, childSegment);
					} else if (!childSegment.equals(knownChildSegment)) {
						folderPaths.add(parent);
					}
				}
			}
		}

		IPath workingDirectory = new Path(repository.getWorkTree()
				.getAbsolutePath());

		List<StagingFolderEntry> folderEntries = new ArrayList<StagingFolderEntry>();
		for (IPath folderPath : folderPaths) {
			IPath parent = folderPath.removeLastSegments(1);
			while (parent.segmentCount() != 0 && !folderPaths.contains(parent))
				parent = parent.removeLastSegments(1);
			if (parent.segmentCount() == 0) {
				StagingFolderEntry folderEntry = new StagingFolderEntry(
						workingDirectory, folderPath, folderPath);
				folderEntries.add(folderEntry);
			} else {
				IPath nodePath = folderPath.makeRelativeTo(parent);
				StagingFolderEntry folderEntry = new StagingFolderEntry(
						workingDirectory, folderPath, nodePath);
				folderEntries.add(folderEntry);
			}
		}

		Collections.sort(folderEntries, FolderComparator.INSTANCE);
		return folderEntries.toArray(new StagingFolderEntry[folderEntries
				.size()]);
	}

	int getShownCount() {
		String filterString = getFilterString();
		if (filterString.length() == 0) {
			return getCount();
		} else {
			int shownCount = 0;
			for (StagingEntry entry : content) {
				if (isInFilter(entry))
					shownCount++;
			}
			return shownCount;
		}
	}

	List<StagingEntry> getStagingEntriesFiltered(StagingFolderEntry folder) {
		List<StagingEntry> stagingEntries = new ArrayList<StagingEntry>();
		for (StagingEntry stagingEntry : content) {
			if (folder.getLocation().isPrefixOf(stagingEntry.getLocation())) {
				if (isInFilter(stagingEntry))
					stagingEntries.add(stagingEntry);
			}
		}
		return stagingEntries;
	}

	boolean isInFilter(StagingEntry stagingEntry) {
		String filterString = getFilterString();
		return filterString.length() == 0
				|| stagingEntry.getPath().toUpperCase()
						.contains(filterString.toUpperCase());
	}

	private String getFilterString() {
		return stagingView.getFilterString();
	}

	boolean hasVisibleChildren(StagingFolderEntry folder) {
		if (getFilterString().length() == 0)
			return true;
		else
			return !getStagingEntriesFiltered(folder).isEmpty();
