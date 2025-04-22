		if (!display.isDisposed()) {
			display.syncExec(new Runnable() {
				@Override
				public void run() {
					Assert.isTrue(workbench.close());
				}
			});
		}
