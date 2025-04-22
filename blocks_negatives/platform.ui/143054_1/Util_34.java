    /**
     * A shared, unmodifiable, empty, sorted map. This value is guaranteed to
     * always be the same.
     */
	public final static SortedMap<?, ?> EMPTY_SORTED_MAP = Collections
			.unmodifiableSortedMap(new TreeMap<>());

    /**
     * A shared, unmodifiable, empty, sorted set. This value is guaranteed to
     * always be the same.
     */
	public final static SortedSet<?> EMPTY_SORTED_SET = Collections.unmodifiableSortedSet(new TreeSet<>());

    /**
     * A shared, zero-length string -- for avoiding non-externalized string
     * tags. This value is guaranteed to always be the same.
     */
