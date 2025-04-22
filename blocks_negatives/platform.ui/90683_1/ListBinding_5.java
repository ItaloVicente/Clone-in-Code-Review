		modelList.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				ListDiff diff = Diffs.computeListDiff(Collections.EMPTY_LIST,
						modelList);
				doUpdate(modelList, (IObservableList) getTarget(), diff,
						modelToTarget, true, true);
			}
