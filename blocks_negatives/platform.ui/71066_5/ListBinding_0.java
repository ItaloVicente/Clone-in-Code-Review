		if ((targetToModel.getUpdatePolicy() & UpdateListStrategy.POLICY_UPDATE) != 0) {
			target.addListChangeListener(targetChangeListener);
		} else {
			targetChangeListener = null;
		}
		if ((modelToTarget.getUpdatePolicy() & UpdateListStrategy.POLICY_UPDATE) != 0) {
			model.addListChangeListener(modelChangeListener);
		} else {
			modelChangeListener = null;
		}
