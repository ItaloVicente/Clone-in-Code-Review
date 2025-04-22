		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				new Snippet023ConditionalVisibility().createControls(shell);
			}
		});
