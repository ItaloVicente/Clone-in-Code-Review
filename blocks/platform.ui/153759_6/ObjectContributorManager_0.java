	protected void removeCommonAdapters(List<String> adapters, List<Class<?>> results) {
		for (Iterator<Class<?>> it = results.iterator(); it.hasNext();) {
			Class<?> clazz = it.next();
			List<Class<?>> commonTypes = computeCombinedOrder(clazz);
			for (Iterator<Class<?>> it2 = commonTypes.iterator(); it2.hasNext();) {
				Class<?> type = it2.next();
