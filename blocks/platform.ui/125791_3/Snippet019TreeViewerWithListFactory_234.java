		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				try {
					Snippet019TreeViewerWithListFactory window = new Snippet019TreeViewerWithListFactory();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
