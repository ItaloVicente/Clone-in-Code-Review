		Thread hook = new Thread() {

			@Override
			public void run() {
				System.out.println("cleanup unfinished clone");
				cleanup();
			}
		};
		Runtime.getRuntime().addShutdownHook(hook);
