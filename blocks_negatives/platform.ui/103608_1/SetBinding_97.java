		modelSet.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				SetDiff diff = Diffs.computeSetDiff(Collections.EMPTY_SET,
						modelSet);
				doUpdate(modelSet, (IObservableSet) getTarget(), diff,
						modelToTarget, true, true);
			}
