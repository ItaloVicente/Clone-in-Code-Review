    @Override
	public int compareTo(Object object) {
        Category castedObject = (Category) object;
        int compareTo = Util.compare(
                categoryActivityBindingsAsArray,
                castedObject.categoryActivityBindingsAsArray);
