
	private final static int TREE = 0;

	private final static int INDEX = 1;

	private final static int WORKDIR = 2;

	private final Repository repository;

	private final RevTree tree;

	private final WorkingTreeIterator initialWorkingTreeIterator;

	private HashSet<String> added = new HashSet<String>();

	private HashSet<String> changed = new HashSet<String>();

	private HashSet<String> removed = new HashSet<String>();

	private HashSet<String> missing = new HashSet<String>();

	private HashSet<String> modified = new HashSet<String>();

	private HashSet<String> untracked = new HashSet<String>();
