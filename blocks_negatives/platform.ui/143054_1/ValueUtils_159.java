    /**
     * Returns the length of the supplied collection. If the supplied object
     * is not a collection, returns 1. If collection is null, returns 0.
     * @param collection to check
     * @return int
     */
    public static int getLength(Object collection) {
        if (collection == null) {
            return 0;
        }
        collection = getValue(collection);
        if (collection.getClass().isArray()) {
            return Array.getLength(collection);
        }
        if (collection instanceof Collection) {
            return ((Collection<?>) collection).size();
        }
        return 1;
    }
