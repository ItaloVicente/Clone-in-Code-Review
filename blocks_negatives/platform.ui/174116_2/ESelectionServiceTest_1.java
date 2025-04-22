		applicationContext.set(UISynchronize.class, new UISynchronize() {
			@Override
			public void syncExec(Runnable runnable) {
				runnable.run();
			}

			@Override
			public void asyncExec(final Runnable runnable) {
				runnable.run();
			}
		});
