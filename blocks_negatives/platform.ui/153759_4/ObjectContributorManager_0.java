	protected void removeCommonAdapters(List adapters, List results) {
		for (Iterator it = results.iterator(); it.hasNext();) {
			Class clazz = ((Class) it.next());
			List commonTypes = computeCombinedOrder(clazz);
			for (Iterator it2 = commonTypes.iterator(); it2.hasNext();) {
				Class type = (Class) it2.next();
