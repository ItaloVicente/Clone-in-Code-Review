			getModel().getRealm().exec(new Runnable() {
				@Override
				public void run() {
					((IObservableSet) getModel()).addSetChangeListener(modelChangeListener);
					updateModelToTarget();
				}
