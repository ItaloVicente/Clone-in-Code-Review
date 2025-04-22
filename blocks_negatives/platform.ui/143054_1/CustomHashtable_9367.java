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

    /**
     * The default capacity used when not specified in the constructor.
     */
    public static final int DEFAULT_CAPACITY = 13;

    /**
     * Constructs a new Hashtable using the default capacity
     * and load factor.
     */
    public CustomHashtable() {
        this(13);
    }

    /**
     * Constructs a new Hashtable using the specified capacity
     * and the default load factor.
     *
     * @param capacity the initial capacity
     */
    public CustomHashtable(int capacity) {
        this(capacity, null);
    }

    /**
     * Constructs a new hash table with the default capacity and the given
     * element comparer.
     *
     * @param comparer the element comparer to use to compare keys and obtain
     *   hash codes for keys, or <code>null</code>  to use the normal
     *   <code>equals</code> and <code>hashCode</code> methods
     */
    public CustomHashtable(IElementComparer comparer) {
        this(DEFAULT_CAPACITY, comparer);
    }

    /**
     * Constructs a new hash table with the given capacity and the given
     * element comparer.
     *
     * @param capacity the maximum number of elements that can be added without
     *   rehashing
     * @param comparer the element comparer to use to compare keys and obtain
     *   hash codes for keys, or <code>null</code>  to use the normal
     *   <code>equals</code> and <code>hashCode</code> methods
     */
    public CustomHashtable(int capacity, IElementComparer comparer) {
        if (capacity >= 0) {
            elementCount = 0;
            elementData = new HashMapEntry[capacity == 0 ? 1 : capacity];
            firstSlot = elementData.length;
            loadFactor = 0.75f;
            computeMaxSize();
        } else {
