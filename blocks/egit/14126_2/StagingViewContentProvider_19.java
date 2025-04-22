	private Object[] getRoots() {
		if (roots == null) {
			List<Object> rootList = new ArrayList<Object>();
			rootFolders = getRootFolders();
			for (StagingFolderEntry rootFolder : rootFolders)
				rootList.add(rootFolder);
			if (rootFiles != null) {
				for (StagingEntry rootFile : rootFiles)
					rootList.add(rootFile);
			}
			roots = new Object[rootList.size()];
			rootList.toArray(roots);
		}
		return roots;
