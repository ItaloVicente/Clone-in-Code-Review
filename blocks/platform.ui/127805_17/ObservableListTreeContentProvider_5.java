		protected void removeCollectionChangeListener(IObservableCollection<?> collection,
				IObservablesListener listener) {
			@SuppressWarnings("unchecked")
			IObservableList<Object> list = (IObservableList<Object>) collection;
			@SuppressWarnings("unchecked")
			IListChangeListener<Object> listListener = (IListChangeListener<Object>) listener;
