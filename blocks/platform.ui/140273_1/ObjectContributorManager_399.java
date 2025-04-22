	private static List removeDups(List list) {
		if (list.size() <= 1) {
			return list;
		}
		HashSet set = new HashSet(list);
		if (set.size() == list.size()) {
			return list;
		}
		ArrayList result = new ArrayList(set.size());
		for (Iterator i = list.iterator(); i.hasNext();) {
			Object o = i.next();
			if (set.remove(o)) {
				result.add(o);
			}
		}
		return result;
	}

	private List getCommonClasses(List objects, List commonAdapters) {
		if (objects == null || objects.isEmpty()) {
