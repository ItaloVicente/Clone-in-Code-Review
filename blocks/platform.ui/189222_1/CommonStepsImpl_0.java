
	static <E> IObservableList<E> createSetterList(Consumer<List<E>> setter) {
		WritableList<E> list = new WritableList<E>();
		list.addListChangeListener(e -> setter.accept(Collections.unmodifiableList(e.getObservableList())));
		return list;
	}

	static <E> IObservableSet<E> createSetterSet(Consumer<Set<E>> setter) {
		WritableSet<E> list = new WritableSet<E>();
		list.addSetChangeListener(e -> setter.accept(Collections.unmodifiableSet(e.getObservableSet())));
		return list;
	}
