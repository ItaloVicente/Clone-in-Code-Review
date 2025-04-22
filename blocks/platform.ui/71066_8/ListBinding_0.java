			getModel().getRealm().exec(new Runnable() {
				@Override
				public void run() {
					((IObservableList) getModel()).addListChangeListener(modelChangeListener);
					updateModelToTarget();
				}
			});
		} else {
			modelChangeListener = null;
