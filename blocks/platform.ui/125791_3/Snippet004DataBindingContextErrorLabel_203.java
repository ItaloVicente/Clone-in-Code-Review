		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				Shell shell = new Shell(display);
				shell.setText("Data Binding Snippet 004");
				shell.setLayout(new GridLayout(2, false));
