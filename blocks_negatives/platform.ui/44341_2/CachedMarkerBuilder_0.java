	void saveState(IMemento memento) {
		getComparator().saveState(memento);
		if (categoryGroup == null)
			memento.putString(TAG_CATEGORY_GROUP, VALUE_NONE);
		else
			memento.putString(TAG_CATEGORY_GROUP, getCategoryGroup().getId());
