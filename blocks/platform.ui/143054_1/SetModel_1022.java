		fireAdd(toAdd);
	}

	public void addAll(Collection toAdd) {
		Assert.isNotNull(toAdd);
		addAll(toAdd.toArray());
	}

	public void changeAll(Object[] changed) {
		Assert.isNotNull(changed);
		fireUpdate(changed);
	}

	public void removeAll(Object[] toRemove) {
		Assert.isNotNull(toRemove);
		for (Object object : toRemove) {
			data.remove(object);
		}
