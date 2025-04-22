    }

    /**
     * Increases the capacity of this Hashtable. This method is sent when
     * the size of this Hashtable exceeds the load factor.
     */
    private void rehash() {
        int length = elementData.length << 1;
        if (length == 0) {
