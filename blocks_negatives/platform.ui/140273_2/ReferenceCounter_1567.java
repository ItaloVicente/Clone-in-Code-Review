        return rec.addRef();
    }

    /**
     * Returns the object defined by an ID.  If the ID is not
     * found <code>null</code> is returned.
     *
     * @return the object or <code>null</code>
     */
    public Object get(Object id) {
        RefRec rec = (RefRec) mapIdToRec.get(id);
        if (rec == null) {
