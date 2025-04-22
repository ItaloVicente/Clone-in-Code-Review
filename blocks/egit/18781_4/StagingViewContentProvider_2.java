		for (StagingFolderEntry folderEntry : folderEntries) {
			List<Object> children = childrenForPath.get(folderEntry.getPath());
			if (children != null) {
				for (Object child : children) {
					if (child instanceof StagingEntry)
						((StagingEntry) child).setParent(folderEntry);
					else if (child instanceof StagingFolderEntry)
						((StagingFolderEntry) child).setParent(folderEntry);
				}
				Collections.sort(children, EntryComparator.INSTANCE);
				folderEntry.setChildren(children.toArray());
			}
		}

		Collections.sort(roots, EntryComparator.INSTANCE);
		return roots.toArray();
	}

	private static void addChild(Map<IPath, List<Object>> childrenForPath,
			IPath path, Object child) {
		List<Object> children = childrenForPath.get(path);
		if (children == null) {
			children = new ArrayList<Object>();
			childrenForPath.put(path, children);
		}
		children.add(child);
