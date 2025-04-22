	private StagingFolderEntry[] folders;

	private StagingFolderEntry[] rootFolders;

	private StagingEntry[] rootFiles;

	private Object[] roots;

	private Object[] compressedRoots;

	private StagingFolderEntry[] compressedFolders;

	private List<StagingFolderEntry> folderList;

	private List<StagingFolderEntry> compressedFolderList;

	private StagingView stagingView;
	private boolean unstagedSection;

	private Repository repository;

	private FolderComparator comparator = new FolderComparator();

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
