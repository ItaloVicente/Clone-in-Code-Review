	private void addRuntimeExceptionHandler() {
		runtimeExceptionHandler = Display.getDefault().getRuntimeExceptionHandler();
		Display.getDefault().setRuntimeExceptionHandler(e -> handle(e, new IEventLoopAdvisor() {
			@Override
			public void eventLoopIdle(Display display) {
				display.sleep();
			}

			@Override
			public void eventLoopException(Throwable exception) {
				StatusReporter statusReporter = appContext.get(StatusReporter.class);
				if (statusReporter != null) {
					statusReporter.report(statusReporter.newStatus(StatusReporter.ERROR, "Internal Error", exception),
							StatusReporter.LOG, exception);
				}
			}
		}));
	}

	private void resetRuntimeExceptionHandler() {
		if (runtimeExceptionHandler != null)
			Display.getDefault().setRuntimeExceptionHandler(runtimeExceptionHandler);
	}

	private void handle(Throwable ex, IEventLoopAdvisor advisor) {
		try {
			advisor.eventLoopException(ex);
		} catch (Throwable t) {
			if (t instanceof ThreadDeath) {
				throw (ThreadDeath) t;
			}
			t.printStackTrace();
		}
	}
	
