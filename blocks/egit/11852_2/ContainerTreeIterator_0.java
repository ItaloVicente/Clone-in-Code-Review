	private void addFilteredEntries(final Set<File> existingResourceEntries,
			final List<Entry> addToEntries) {
		IPath containerLocation = node.getLocation();
		if (containerLocation == null)
			return;

		File folder = containerLocation.toFile();
		File[] children = folder.listFiles();
		if (children == null)
			return;

		for (File child : children) {
			if (existingResourceEntries.contains(child))
				continue; // ok if linked resources are ignored earlier on
			IPath childLocation = new Path(child.getAbsolutePath());
			IWorkspaceRoot root = node.getWorkspace().getRoot();
			IContainer container = root.getContainerForLocation(childLocation);
			if (container != null && container.isAccessible())
				addToEntries.add(new ResourceEntry(container, false));
			else
				addToEntries.add(new FileEntry(child, FS.DETECTED));
		}
	}

