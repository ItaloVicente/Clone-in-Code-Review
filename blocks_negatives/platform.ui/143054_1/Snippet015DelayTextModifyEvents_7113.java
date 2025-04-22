		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell();
				shell.setLayout(new GridLayout(3, false));

				createControls(shell);
