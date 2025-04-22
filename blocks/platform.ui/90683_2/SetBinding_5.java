			getTarget().getRealm().exec(() -> {
				((IObservableSet) getTarget()).addSetChangeListener(targetChangeListener);
				if (modelToTarget.getUpdatePolicy() == UpdateSetStrategy.POLICY_NEVER) {
					updateTargetToModel();
				} else {
					validateTargetToModel();
