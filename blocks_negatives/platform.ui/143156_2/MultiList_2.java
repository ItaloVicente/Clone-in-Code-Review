		throw new IndexOutOfBoundsException("index: " + index + ", size: " //$NON-NLS-1$ //$NON-NLS-2$
				+ offset);
	}

	@Override
	public Object[] toArray() {
		getterCalled();
		return toArray(new Object[doGetSize()]);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		getterCalled();
		return super.toArray(a);
