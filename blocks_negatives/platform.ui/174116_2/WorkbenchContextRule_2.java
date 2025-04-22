		context.set(UISynchronize.class, new UISynchronize() {
			@Override
			public void syncExec(Runnable runnable) {
				display.syncExec(runnable);
			}

			@Override
			public void asyncExec(Runnable runnable) {
				display.asyncExec(runnable);
			}
		});
