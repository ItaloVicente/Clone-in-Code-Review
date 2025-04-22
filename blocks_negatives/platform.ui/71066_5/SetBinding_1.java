		if ((targetToModel.getUpdatePolicy() & UpdateSetStrategy.POLICY_UPDATE) != 0) {
			target.addSetChangeListener(targetChangeListener);
		} else {
			targetChangeListener = null;
		}
		if ((modelToTarget.getUpdatePolicy() & UpdateSetStrategy.POLICY_UPDATE) != 0) {
			model.addSetChangeListener(modelChangeListener);
		} else {
			modelChangeListener = null;
		}
