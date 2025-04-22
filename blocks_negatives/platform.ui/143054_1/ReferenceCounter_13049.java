        return rec.refCount;
    }

    /**
     * Removes one reference from an object in the counter.
     * If the ref count drops to 0 the object is removed from
     * the counter completely.
     *
     * @param id is a unique ID for the object.
     * @return the new ref count
     */
    public int removeRef(Object id) {
    	RefRec rec = (RefRec) mapIdToRec.get(id);
    	if (rec == null) {
    		return 0;
    	}
    	int newCount = rec.removeRef();
    	if (newCount <= 0) {
    		mapIdToRec.remove(id);
    	}
    	return newCount;
    }

    /**
     * Returns a complete list of the values in the counter.
     *
     * @return a Collection containing the values.
     */
    public List values() {
        int size = mapIdToRec.size();
        ArrayList list = new ArrayList(size);
        Iterator iter = mapIdToRec.values().iterator();
        while (iter.hasNext()) {
            RefRec rec = (RefRec) iter.next();
            list.add(rec.getValue());
        }
        return list;
    }
