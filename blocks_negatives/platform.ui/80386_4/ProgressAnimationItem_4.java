		final Display display = Display.getDefault();
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				refresh();
			}
		});
