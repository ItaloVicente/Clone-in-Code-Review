	private Object[] getCompressedRoots() {
		if (compressedRoots == null) {
			List<Object> compressedRootList = new ArrayList<Object>();
			compressedFolders = getCompressedFolders();
			for (StagingFolderEntry compressedFolder : compressedFolders)
				compressedRootList.add(compressedFolder);
			for (StagingEntry rootFile : rootFiles)
				compressedRootList.add(rootFile);
			compressedRoots = new Object[compressedRootList.size()];
			compressedRootList.toArray(compressedRoots);
		}
		return compressedRoots;
	}

	private StagingFolderEntry[] getRootFolders() {
		if (content == null || content.length == 0)
			return new StagingFolderEntry[0];
		if (rootFolders == null)
			getFolders();
		return rootFolders;
	}

	@SuppressWarnings("unchecked")
	private StagingFolderEntry[] getFolders() {
		if (folders == null) {
			String workTreePath = stagingView.getCurrentRepository()
					.getWorkTree().getAbsolutePath();
			List<StagingFolderEntry> rootFolderList = new ArrayList<StagingFolderEntry>();
			List<StagingEntry> rootFileList = new ArrayList<StagingEntry>();
			folderList = new ArrayList<StagingFolderEntry>();
			List<String> resourceList = new ArrayList<String>();
			for (StagingEntry stagingEntry : content) {
				File parent = stagingEntry.getSystemFile();
				if (parent == null
						|| (parent.getParentFile() != null
						&& parent.getParentFile().getAbsolutePath()
								.equals(workTreePath))) {
					rootFileList.add(stagingEntry);
					continue;
				}

				while (parent != null
						&& !parent.getAbsolutePath()
								.equals(workTreePath)) {
					if (!parent.getParentFile().getAbsolutePath()
									.equals(workTreePath)
							&& resourceList.contains(parent.getParentFile()
									.getAbsolutePath()))
						break;
					if (parent.getParentFile() == null
							|| parent.getParentFile().getAbsolutePath()
									.equals(workTreePath)) {
						rootFolderList.add(new StagingFolderEntry(parent));
						resourceList.add(parent.getAbsolutePath());
					}
					parent = parent.getParentFile();
					if (parent != null) {
						folderList.add(new StagingFolderEntry(parent));
						resourceList.add(parent.getAbsolutePath());
					}
				}
			}
			folders = new StagingFolderEntry[folderList.size()];
			folderList.toArray(folders);
			Arrays.sort(folders, comparator);
			rootFolders = new StagingFolderEntry[rootFolderList.size()];
			rootFolderList.toArray(rootFolders);
			rootFiles = new StagingEntry[rootFileList.size()];
			rootFileList.toArray(rootFiles);
			Arrays.sort(rootFolders, comparator);
		}
		return folders;
	}

	private Object[] getFolderChildren(StagingFolderEntry parent) {
		List<Object> children = new ArrayList<Object>();
		folders = getFolders();
		for (StagingFolderEntry folder : folders) {
			if (folder.getFile().getParentFile() != null
					&& folder.getFile().getParentFile()
							.equals(parent.getFile())) {
				folder.setParent(parent);
				children.add(folder);
			}
		}
		for (StagingEntry file : content) {
			if (file.getSystemFile().getParentFile() != null
					&& file.getSystemFile().getParentFile()
							.equals(parent.getFile())) {
				file.setParent(parent);
				children.add(file);
			}
		}
		Object[] childArray = new Object[children.size()];
		children.toArray(childArray);
		return childArray;
	}

	@SuppressWarnings("unchecked")
	private StagingFolderEntry[] getCompressedFolders() {
		if (compressedFolders == null) {
			String workTreePath = stagingView.getCurrentRepository()
					.getWorkTree().getAbsolutePath();
			List<File> parentList = new ArrayList<File>();
			List<StagingEntry> rootFileList = new ArrayList<StagingEntry>();
			compressedFolderList = new ArrayList<StagingFolderEntry>();
			for (StagingEntry file : content) {
				File parentFile = file.getSystemFile().getParentFile();

				if (parentFile != null
						&& parentFile.getAbsolutePath().equals(workTreePath)) {
					rootFileList.add(file);
					continue;
				}

				if (!parentList.contains(parentFile)) {
					compressedFolderList
							.add(new StagingFolderEntry(
							parentFile));
					parentList.add(parentFile);
				}
			}
			compressedFolders = new StagingFolderEntry[compressedFolderList
					.size()];
			compressedFolderList.toArray(compressedFolders);
			Arrays.sort(compressedFolders, comparator);
			rootFiles = new StagingEntry[rootFileList.size()];
			rootFileList.toArray(rootFiles);
		}
		return compressedFolders;
	}

	private StagingEntry[] getChildResources(StagingFolderEntry parent) {
		File parentFile = parent.getFile();
		List<StagingEntry> children = new ArrayList<StagingEntry>();
		for (StagingEntry file : content) {
			if (file.getSystemFile().getParentFile().equals(parentFile)) {
				file.setParent(parent);
				children.add(file);
			}
		}
		StagingEntry[] childArray = new StagingEntry[children.size()];
		children.toArray(childArray);
		return childArray;
	}

	int getShownCount(String filterText) {
		if (filterText == null || filterText.length() == 0) {
			return getCount();
		} else {
			int shownCount = 0;
			for (StagingEntry entry : content) {
				if (entry.getPath().toUpperCase()
						.contains(filterText.toUpperCase()))
					shownCount++;
			}
			return shownCount;
		}
	}

	StagingEntry[] getStagingEntries(StagingFolderEntry folder,
			String filterText) {
		List<StagingEntry> stagingEntries = new ArrayList<StagingEntry>();
		for (StagingEntry stagingEntry : content) {
			if (stagingEntry.getLocation().toString()
					.startsWith(folder.getPath() + "/")) { //$NON-NLS-1$
				if (filterText == null
						|| filterText.length() == 0
						|| stagingEntry.getPath().toUpperCase()
								.contains(filterText.toUpperCase()))
					stagingEntries.add(stagingEntry);
			}
		}
		StagingEntry[] stagingEntryArray = new StagingEntry[stagingEntries
				.size()];
		stagingEntries.toArray(stagingEntryArray);
		return stagingEntryArray;
	}

	boolean hasVisibleChildren(StagingFolderEntry folder, String filterText) {
		if (filterText == null || filterText.length() == 0)
			return true;
		return getStagingEntries(folder, filterText).length > 0;
	}

	StagingEntry[] getStagingEntries() {
		return content;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
