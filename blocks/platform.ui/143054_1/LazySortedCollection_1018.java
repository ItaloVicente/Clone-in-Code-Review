	private final int MIN_CAPACITY = 8;
	private Object[] contents = new Object[MIN_CAPACITY];
	private int[] leftSubTree = new int[MIN_CAPACITY];
	private int[] rightSubTree = new int[MIN_CAPACITY];
	private int[] nextUnsorted = new int[MIN_CAPACITY];
	private int[] treeSize = new int[MIN_CAPACITY];
	private int[] parentTree = new int[MIN_CAPACITY];
	private int root = -1;
	private int lastNode = 0;
	private int firstUnusedNode = -1;

	private static final float loadFactor = 0.75f;

	private IntHashMap objectIndices;
	private Comparator comparator;
	private static int counter = 0;

	public boolean enableDebug = false;

	private Object lazyRemovalFlag = new Object() {
		@Override
