        return new HashEnumerator(true);
    }

    /**
     * Associate the specified value with the specified key in this Hashtable.
     * If the key already exists, the old value is replaced. The key and value
     * cannot be null.
     *
     * @param		key	the key to add
     * @param		value	the value to add
     * @return		the old value associated with the specified key, null if the key did
     *				not exist
     */
    public Object put(Object key, Object value) {
        if (key != null && value != null) {
            int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
            HashMapEntry entry = elementData[index];
            while (entry != null && !keyEquals(key, entry.key)) {
