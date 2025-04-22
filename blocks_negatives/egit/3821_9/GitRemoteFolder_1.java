	GitRemoteResource[] members(IProgressMonitor monitor) throws IOException {
		Repository repo = getRepo();
		TreeWalk tw = new TreeWalk(repo);
		int nth = tw.addTree(getObjectId());

		IProgressMonitor m = SubMonitor.convert(monitor);
		m.beginTask(NLS.bind(CoreText.GitFolderResourceVariant_fetchingMembers,
				this), tw.getTreeCount());
