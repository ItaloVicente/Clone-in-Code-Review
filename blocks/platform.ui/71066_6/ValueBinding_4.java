
		if (targetToModel.getUpdatePolicy() == UpdateValueStrategy.POLICY_UPDATE) {
			target.addValueChangeListener(targetChangeListener);
			if (modelToTarget.getUpdatePolicy() == UpdateValueStrategy.POLICY_NEVER) {
				updateTargetToModel();
			} else {
				validateTargetToModel();
			}
		} else if (targetToModel.getUpdatePolicy() == UpdateValueStrategy.POLICY_CONVERT) {
			target.addValueChangeListener(targetChangeListener);
