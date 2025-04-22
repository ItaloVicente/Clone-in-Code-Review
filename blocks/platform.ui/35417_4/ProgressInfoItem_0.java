
	public boolean isTriggerEnabled() {
		return link != null && !link.isDisposed() && link.isEnabled();
	}

	private void hookTriggerCommandEnablement() {
		final Object data = link.getData(TRIGGER_KEY);
		if (!(data instanceof ParameterizedCommand) || !PlatformUI.isWorkbenchRunning())
			return;

		IEclipseContext context = PlatformUI.getWorkbench().getService(IEclipseContext.class);
		if (context == null) {
			return;
		}
		if (tracker != null) {
			tracker.stop();
		}
		tracker = new HandlerChangeTracker((ParameterizedCommand) data);
		context.runAndTrack(tracker);
	}

	private class HandlerChangeTracker extends RunAndTrack {
		private ParameterizedCommand parmCommand;
		private boolean stop = false;

		public HandlerChangeTracker(ParameterizedCommand parmCommand) {
			this.parmCommand = parmCommand;
		}

		public void stop() {
			this.stop = true;
		}

		@Override
		public boolean changed(IEclipseContext context) {
			if (stop || isDisposed() || !isShowing) {
				return false;
			}
			EHandlerService service = context.get(EHandlerService.class);
			link.setEnabled(service != null && service.canExecute(parmCommand));
			return true;
		}
	}
