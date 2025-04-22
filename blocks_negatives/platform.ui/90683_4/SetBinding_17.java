		targetSet.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				SetDiff diff = Diffs.computeSetDiff(Collections.EMPTY_SET,
						targetSet);
				doUpdate(targetSet, (IObservableSet) getModel(), diff,
						targetToModel, true, true);
			}
