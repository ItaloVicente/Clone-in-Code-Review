		if ((targetToModel.getUpdatePolicy() & (UpdateValueStrategy.POLICY_CONVERT | UpdateValueStrategy.POLICY_UPDATE)) != 0) {
			target.addValueChangeListener(targetChangeListener);
		} else {
			targetChangeListener = null;
		}
		if ((modelToTarget.getUpdatePolicy() & (UpdateValueStrategy.POLICY_CONVERT | UpdateValueStrategy.POLICY_UPDATE)) != 0) {
			model.addValueChangeListener(modelChangeListener);
		} else {
			modelChangeListener = null;
		}
