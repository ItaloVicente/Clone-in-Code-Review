	}

	public void clear() {
		Object[] removed = data.toArray();
		data.clear();
		fireRemove(removed);
	}

	public void addAll(Object[] toAdd) {
		Assert.isNotNull(toAdd);
		for (Object object : toAdd) {
			data.add(object);
		}
