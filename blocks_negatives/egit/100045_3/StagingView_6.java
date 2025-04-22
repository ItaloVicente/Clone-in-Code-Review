		asyncExec(new Runnable() {

			@Override
			public void run() {
				if (isDisposed()) {
					return;
				}
