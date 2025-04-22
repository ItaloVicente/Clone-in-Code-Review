		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				display.disposeExec(new Runnable() {
					@Override
					public void run() {
						thread.shutdown();
					}
				});
				thread.start();
			}
