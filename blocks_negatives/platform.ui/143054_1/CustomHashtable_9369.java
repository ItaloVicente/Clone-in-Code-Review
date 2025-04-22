        return new HashEnumerator(false);
    }

    /**
     * Answers the value associated with the specified key in
     * this Hashtable.
     *
     * @param		key	the key of the value returned
     * @return		the value associated with the specified key, null if the specified key
     *				does not exist
     */
    public Object get(Object key) {
        int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
        HashMapEntry entry = elementData[index];
        while (entry != null) {
            if (keyEquals(key, entry.key)) {
