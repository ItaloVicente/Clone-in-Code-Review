			@Override
			public void handleRemove(int index, IObservable dependency) {
				dependency.removeChangeListener(dependencyListener);
				dependency.removeStaleListener(dependencyListener);
			}
		});
	};
