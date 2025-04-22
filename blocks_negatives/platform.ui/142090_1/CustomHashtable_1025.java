    /**
     * HashMapEntry is an internal class which is used to hold the entries of a Hashtable.
     */
    private static class HashMapEntry {
        Object key, value;

        HashMapEntry next;

        HashMapEntry(Object theKey, Object theValue) {
            key = theKey;
            value = theValue;
        }
    }

    private static final class EmptyEnumerator implements Enumeration {
        @Override
