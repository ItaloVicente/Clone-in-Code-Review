			getTarget().getRealm().exec(new Runnable() {
				@Override
				public void run() {
					((IObservableList) getTarget()).addListChangeListener(targetChangeListener);
					if (modelToTarget.getUpdatePolicy() == UpdateListStrategy.POLICY_NEVER) {
						updateTargetToModel();
					} else {
						validateTargetToModel();
					}
				}
			});
		} else {
			targetChangeListener = null;
