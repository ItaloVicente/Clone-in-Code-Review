	private boolean prepareTrees(IFile[] selectedItems,
			HashMap<Repository, Tree> treeMap, IProgressMonitor monitor)
			throws IOException, UnsupportedEncodingException {
		if (selectedItems.length == 0) {
			for (Repository repo : repos) {
				treeMap.put(repo, mapTree(repo, Constants.HEAD));
			}
		}
