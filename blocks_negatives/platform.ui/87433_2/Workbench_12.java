		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				final String nlExtensions = Platform.getNLExtensions();
				if (nlExtensions.length() > 0) {
					ULocale.setDefault(Category.FORMAT,
							new ULocale(ULocale.getDefault(Category.FORMAT).getBaseName()
									+ nlExtensions));
				}
