		appContext.set(UISynchronize.class, new UISynchronize() {

			@Override
			public void syncExec(Runnable runnable) {
				runnable.run();
			}

			@Override
			public void asyncExec(Runnable runnable) {
				runnable.run();
			}
		});
