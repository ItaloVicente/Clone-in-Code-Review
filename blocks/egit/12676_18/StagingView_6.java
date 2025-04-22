	private Presentation presentation = Presentation.LIST;

	private Set<IPath> pathsToExpandInStaged = new HashSet<IPath>();

	private Set<IPath> pathsToExpandInUnstaged = new HashSet<IPath>();

	public enum Presentation {
		LIST,
		TREE,
		COMPACT_TREE;
	}

