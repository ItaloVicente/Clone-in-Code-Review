		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				try {
					Snippet020TreeViewerWithSetFactory window = new Snippet020TreeViewerWithSetFactory();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
