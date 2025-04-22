	private IValueChangeListener targetChangeListener = new IValueChangeListener() {
		@Override
		public void handleValueChange(ValueChangeEvent event) {
			if (!updatingTarget
					&& !Util.equals(event.diff.getOldValue(), event.diff
							.getNewValue())) {
				doUpdate(target, model, targetToModel, false, false);
			}
		}
	};
	private IValueChangeListener modelChangeListener = new IValueChangeListener() {
		@Override
		public void handleValueChange(ValueChangeEvent event) {
			if (!updatingModel
					&& !Util.equals(event.diff.getOldValue(), event.diff
							.getNewValue())) {
				doUpdate(model, target, modelToTarget, false, false);
			}
		}
	};
