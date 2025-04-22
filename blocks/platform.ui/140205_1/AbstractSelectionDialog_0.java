	public T getFirstResult() {
		Collection<T> list = getResult();
		if (list == null) {
			return null;
		}
		Iterator<T> iterator = list.iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}
		return null;
	}

