	private StagingFolderEntry[] treeFolders;

	private StagingFolderEntry[] compactTreeFolders;

	private StagingView stagingView;
	private boolean unstagedSection;

	private Repository repository;

	StagingViewContentProvider(StagingView stagingView, boolean unstagedSection) {
		this.stagingView = stagingView;
		this.unstagedSection = unstagedSection;
	}

	public Object getParent(Object element) {
		if (element instanceof StagingFolderEntry)
			return ((StagingFolderEntry) element).getParent();
		if (element instanceof StagingEntry)
			return ((StagingEntry) element).getParent();
		return null;
	}

	public boolean hasChildren(Object element) {
		return !(element instanceof StagingEntry);
