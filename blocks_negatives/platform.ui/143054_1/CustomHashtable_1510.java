            entry = entry.next;
        }
        return null;
    }

    /**
     * Answers the hash code for the given key.
     */
    private int hashCode(Object key) {
        if (comparer == null) {
