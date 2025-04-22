		}
	}

	transient int elementCount;

	transient HashMapEntry[] elementData;

	private float loadFactor;

	private int threshold;

	transient int firstSlot = 0;

	transient int lastSlot = -1;

	transient private IElementComparer comparer;

	private static final EmptyEnumerator emptyEnumerator = new EmptyEnumerator();

	public static final int DEFAULT_CAPACITY = 13;

	public CustomHashtable() {
		this(13);
	}

	public CustomHashtable(int capacity) {
		this(capacity, null);
	}

	public CustomHashtable(IElementComparer comparer) {
		this(DEFAULT_CAPACITY, comparer);
	}

	public CustomHashtable(int capacity, IElementComparer comparer) {
		if (capacity >= 0) {
			elementCount = 0;
			elementData = new HashMapEntry[capacity == 0 ? 1 : capacity];
			firstSlot = elementData.length;
			loadFactor = 0.75f;
			computeMaxSize();
		} else {
