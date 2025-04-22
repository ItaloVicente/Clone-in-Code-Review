		targetList.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				ListDiff diff = Diffs.computeListDiff(Collections.EMPTY_LIST,
						targetList);
				doUpdate(targetList, (IObservableList) getModel(), diff,
						targetToModel, true, true);
			}
