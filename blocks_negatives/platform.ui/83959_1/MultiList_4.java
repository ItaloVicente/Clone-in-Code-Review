	@Override
	public boolean equals(Object o) {
		getterCalled();
		if (o == this)
			return true;
		if (o == null)
			return false;
		if (!(o instanceof List))
			return false;
		List<?> that = (List<?>) o;
		if (doGetSize() != that.size())
			return false;

		int subListIndex = 0;
		for (IObservableList<E> list : lists) {
			List<?> subList = that.subList(subListIndex, subListIndex + list.size());
			if (!list.equals(subList)) {
				return false;
			}
			subListIndex += list.size();
		}
		return true;
	}

	@Override
	public int hashCode() {
		getterCalled();
		int result = 1;
		for (IObservableList<E> list : lists) {
			result = result * 31 + list.hashCode();
		}
		return result;
	}

