	private IValueChangeListener<M> modelChangeListener = new IValueChangeListener<M>() {
		@Override
		public void handleValueChange(ValueChangeEvent<? extends M> event) {
			if (!updatingModel && !Util.equals(event.diff.getOldValue(), event.diff.getNewValue())) {
				doUpdate(model, target, modelToTarget, false, false);
			}
