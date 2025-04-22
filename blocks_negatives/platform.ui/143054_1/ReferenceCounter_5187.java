        return rec.getValue();
    }

    /**
     * Returns a complete list of the keys in the counter.
     *
     * @return a Set containing the ID for each.
     */
    public Set keySet() {
        return mapIdToRec.keySet();
    }

    /**
     * Adds an object to the counter for counting and gives
     * it an initial ref count of 1.
     *
     * @param id is a unique ID for the object.
     * @param value is the object itself.
     */
    public void put(Object id, Object value) {
        RefRec rec = new RefRec(id, value);
        mapIdToRec.put(id, rec);
    }

    /**
     * @param id is a unique ID for the object.
     * @return the current ref count
     */
    public int getRef(Object id) {
        RefRec rec = (RefRec) mapIdToRec.get(id);
        if (rec == null) {
