	@Component(property = EventConstants.EVENT_TOPIC + '='
			+ UIEvents.UILifeCycle.APP_STARTUP_COMPLETE)
	public static class Checker extends Job implements EventHandler {

		public Checker() {
			super(UIText.ConfigurationChecker_checkConfiguration);
			setSystem(true);
			setUser(false);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			SubMonitor progress = SubMonitor.convert(monitor, 2);
			checkHome(progress.newChild(1));
			checkLfs(progress.newChild(1));
			return Status.OK_STATUS;
		}

		@Override
		public void handleEvent(Event event) {
			if (UIEvents.UILifeCycle.APP_STARTUP_COMPLETE
					.equals(event.getTopic())) {
				schedule();
