    /**
     * Return the elements in this list in an array of the given type.
     *
     * @param type the type of the array to create
     * @return the elements in the list
     * @since 3.1
     */
    public Object[] getTypedChildren(Class type) {
		return children.toArray((Object[]) Array.newInstance(type, children
				.size()));
