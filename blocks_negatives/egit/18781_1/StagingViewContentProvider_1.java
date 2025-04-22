			else {
				StagingFolderEntry[] allFolders = getStagingFolderEntries();
				List<Object> roots = new ArrayList<Object>();
				for (StagingFolderEntry folder : allFolders)
					if (folder.getParentPath().segmentCount() == 0)
						roots.add(folder);
				for (StagingEntry entry : content)
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
