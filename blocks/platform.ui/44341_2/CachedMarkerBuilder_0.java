	void saveState(IMemento m) {
		getComparator().saveState(m);
		if (categoryGroup == null) {
			m.putString(TAG_CATEGORY_GROUP, VALUE_NONE);
		} else {
			m.putString(TAG_CATEGORY_GROUP, getCategoryGroup().getId());
		}
