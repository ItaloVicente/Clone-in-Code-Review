	private void collectChildren(Object element, ArrayList result) {
		Object[] filteredChildren = getFilteredChildren(element);
		for (Object curr : filteredChildren) {
			result.add(curr);
			collectChildren(curr, result);
		}
	}
