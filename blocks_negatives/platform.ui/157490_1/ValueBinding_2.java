	private IValueChangeListener<T> targetChangeListener = new IValueChangeListener<T>() {
		@Override
		public void handleValueChange(ValueChangeEvent<? extends T> event) {
			if (!updatingTarget && !Util.equals(event.diff.getOldValue(), event.diff.getNewValue())) {
				doUpdate(target, model, targetToModel, false, false);
			}
