			getTarget().getRealm().exec(() -> {
				((IObservableList) getTarget()).addListChangeListener(targetChangeListener);
				if (modelToTarget.getUpdatePolicy() == UpdateListStrategy.POLICY_NEVER) {
					updateTargetToModel();
				} else {
					validateTargetToModel();
