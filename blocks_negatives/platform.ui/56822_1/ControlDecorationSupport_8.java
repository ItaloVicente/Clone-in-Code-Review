	private IListChangeListener targetsChangeListener = new IListChangeListener() {
		@Override
		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(new ListDiffVisitor() {
				@Override
				public void handleAdd(int index, Object element) {
					targetAdded((IObservable) element);
				}

				@Override
				public void handleRemove(int index, Object element) {
					targetRemoved((IObservable) element);
				}
			});
			statusChanged((IStatus) validationStatus.getValue());
		}
