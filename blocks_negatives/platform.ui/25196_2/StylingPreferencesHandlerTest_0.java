	public void testOnDisplayDisposed() throws Exception {
		final boolean[] result = new boolean[] {false};
		final Throwable[] exceptionDuringExecution = new Throwable[] {null};

		Thread threadForNewDisplay = new Thread(new Runnable() {
			public void run() {
				try {
					Display display = new Display();

					new StylingPreferencesHandler(display) {
						@Override
						protected void resetOverriddenPreferences() {
							result[0] = true;
						}
					};

					display.dispose();

				} catch(Throwable exc) {
					exceptionDuringExecution[0] = exc;
				}
