		container = new ArrayList<Edit>(capacity);
	}

	@Override
	public int size() {
		return container.size();
	}

	@Override
	public Edit get(final int index) {
		return container.get(index);
	}

	@Override
	public Edit set(final int index, final Edit element) {
		return container.set(index, element);
	}

	@Override
	public void add(final int index, final Edit element) {
		container.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends Edit> c) {
		return container.addAll(c);
	}

	@Override
	public Edit remove(final int index) {
		return container.remove(index);
	}

	@Override
	public int hashCode() {
		return container.hashCode();
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof EditList)
			return container.equals(((EditList) o).container);
		return false;
