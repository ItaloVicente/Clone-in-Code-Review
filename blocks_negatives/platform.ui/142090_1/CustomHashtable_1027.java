        this.comparer = comparer;
    }

    /**
     * Constructs a new hash table with enough capacity to hold all keys in the
     * given hash table, then adds all key/value pairs in the given hash table
     * to the new one, using the given element comparer.
     * @param table the original hash table to copy from
     *
     * @param comparer the element comparer to use to compare keys and obtain
     *   hash codes for keys, or <code>null</code>  to use the normal
     *   <code>equals</code> and <code>hashCode</code> methods
     */
    public CustomHashtable(CustomHashtable table, IElementComparer comparer) {
        this(table.size() * 2, comparer);
        for (int i = table.elementData.length; --i >= 0;) {
            HashMapEntry entry = table.elementData[i];
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    /**
     * Returns the element comparer used  to compare keys and to obtain
     * hash codes for keys, or <code>null</code> if no comparer has been
     * provided.
     *
     * @return the element comparer or <code>null</code>
     *
     * @since 3.2
     */
    public IElementComparer getComparer() {
    	return comparer;
    }

    private void computeMaxSize() {
        threshold = (int) (elementData.length * loadFactor);
    }

    /**
     * Answers if this Hashtable contains the specified object as a key
     * of one of the key/value pairs.
     *
     * @param		key	the object to look for as a key in this Hashtable
     * @return		true if object is a key in this Hashtable, false otherwise
     */
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    /**
     * Answers an Enumeration on the values of this Hashtable. The
     * results of the Enumeration may be affected if the contents
     * of this Hashtable are modified.
     *
     * @return		an Enumeration of the values of this Hashtable
     */
    public Enumeration elements() {
        if (elementCount == 0) {
