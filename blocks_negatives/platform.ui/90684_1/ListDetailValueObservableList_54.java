	private IValueChangeListener<E> detailValueListener = new IValueChangeListener<E>() {
		@Override
		public void handleValueChange(ValueChangeEvent<? extends E> event) {
			if (!event.getObservable().isStale()) {
				staleDetailObservables.remove(event.getObservable());
			}
			handleDetailValueChange(event);
