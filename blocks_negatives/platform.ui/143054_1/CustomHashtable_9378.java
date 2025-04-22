                HashMapEntry next = entry.next;
                entry.next = newData[index];
                newData[index] = entry;
                entry = next;
            }
        }
        elementData = newData;
        computeMaxSize();
    }

    /**
     * Remove the key/value pair with the specified key from this Hashtable.
     *
     * @param		key	the key to remove
     * @return		the value associated with the specified key, null if the specified key
     *				did not exist
     */
    public Object remove(Object key) {
        HashMapEntry last = null;
        int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
        HashMapEntry entry = elementData[index];
        while (entry != null && !keyEquals(key, entry.key)) {
            last = entry;
            entry = entry.next;
        }
        if (entry != null) {
            if (last == null) {
