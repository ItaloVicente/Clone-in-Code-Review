		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			try {
				Snippet020TreeViewerWithSetFactory window = new Snippet020TreeViewerWithSetFactory();
				window.open();
			} catch (Exception e) {
				e.printStackTrace();
