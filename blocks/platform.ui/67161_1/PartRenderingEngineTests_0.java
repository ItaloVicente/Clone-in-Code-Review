
		final Display d = Display.getDefault();
		appContext.set(Realm.class, DisplayRealm.getRealm(d));
		appContext.set(UISynchronize.class, new UISynchronize() {
			@Override
			public void syncExec(Runnable runnable) {
				d.syncExec(runnable);
			}

			@Override
			public void asyncExec(Runnable runnable) {
				d.asyncExec(runnable);
			}
		});

