	private static IObservableFactory<Collection<String>, IObservableList<String>> getListDetailFactory() {
		return target -> {
			WritableList<String> list = WritableList.withElementType(String.class);
			list.addAll(target);
			list.addAll(target);
			return list;
		};
	}
