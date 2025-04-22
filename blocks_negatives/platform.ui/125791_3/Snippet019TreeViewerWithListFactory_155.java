		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			try {
				Snippet019TreeViewerWithListFactory window = new Snippet019TreeViewerWithListFactory();
				window.open();
			} catch (Exception e) {
				e.printStackTrace();
