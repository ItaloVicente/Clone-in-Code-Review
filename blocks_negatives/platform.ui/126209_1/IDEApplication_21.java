		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
