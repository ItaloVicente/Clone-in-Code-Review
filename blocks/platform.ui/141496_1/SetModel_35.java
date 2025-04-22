	private HashSet data = new HashSet();

	public Object[] getElements() {
		return data.toArray();
	}

	public void set(Object[] newContents) {
		Assert.isNotNull(newContents);
		data.clear();
		for (Object object : newContents) {
			data.add(object);
		}
