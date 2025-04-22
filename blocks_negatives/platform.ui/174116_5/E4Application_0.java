		appContext.set(UISynchronize.class, new UISynchronize() {

			@Override
			public void syncExec(Runnable runnable) {
				if (display != null && !display.isDisposed()) {
					display.syncExec(runnable);
				}
			}

			@Override
			public void asyncExec(Runnable runnable) {
				if (display != null && !display.isDisposed()) {
					display.asyncExec(runnable);
				}
			}
		});
