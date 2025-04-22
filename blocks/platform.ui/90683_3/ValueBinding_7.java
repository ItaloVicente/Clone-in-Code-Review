		modelChangeListener = event -> {
			if (!updatingModel && !Util.equals(event.diff.getOldValue(), event.diff.getNewValue())) {
				doUpdate(model, target, modelToTarget, false, false);
			}
		};
		targetChangeListener = event -> {
			if (!updatingTarget && !Util.equals(event.diff.getOldValue(), event.diff.getNewValue())) {
				doUpdate(target, model, targetToModel, false, false);
			}
		};
