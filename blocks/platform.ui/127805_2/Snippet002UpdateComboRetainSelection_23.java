	private static IObservableFactory<Object, IObservableList<String>> getListDetailFactory() {
		return target -> {
			WritableList<String> list = WritableList.withElementType(String.class);
			@SuppressWarnings("unchecked")
			Collection<String> targetList = (Collection<String>) target;
			list.addAll(targetList);
			list.addAll(targetList);
			return list;
		};
	}
