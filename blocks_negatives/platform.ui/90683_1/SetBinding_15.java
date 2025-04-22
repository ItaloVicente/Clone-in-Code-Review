			getTarget().getRealm().exec(new Runnable() {
				@Override
				public void run() {
					((IObservableSet) getTarget()).addSetChangeListener(targetChangeListener);
					if (modelToTarget.getUpdatePolicy() == UpdateSetStrategy.POLICY_NEVER) {
						updateTargetToModel();
					} else {
						validateTargetToModel();
					}
