		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				Snippet022ComputedListCombo snippet = new Snippet022ComputedListCombo();
				snippet.createModel();
				snippet.createControls(shell);
			}
