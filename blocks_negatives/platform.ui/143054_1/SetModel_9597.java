    private HashSet data = new HashSet();

    /**
     * Return the contents of the model.
     * @return the array of elements
     *
     */
    public Object[] getElements() {
        return data.toArray();
    }

    /**
     * Sets the contents to the given array of elements
     *
     * @param newContents new contents of this set
     */
    public void set(Object[] newContents) {
    	Assert.isNotNull(newContents);
    	data.clear();
        for (Object object : newContents) {
            data.add(object);
        }
