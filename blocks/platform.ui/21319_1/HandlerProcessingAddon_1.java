	@Inject
	public void handleEvent(@Optional @EventTopic(UIEvents.Context.TOPIC_CONTEXT) Event event) {
		if (event == null)
			return;
		System.err.println("in there"); //$NON-NLS-1$
		Object origin = event.getProperty(UIEvents.EventTags.ELEMENT);
		Object context = event.getProperty(UIEvents.EventTags.NEW_VALUE);
		if ((origin instanceof MHandlerContainer)
				&& (UIEvents.EventTypes.SET.equals(event.getProperty(UIEvents.EventTags.TYPE)) && context instanceof IEclipseContext)) {
			MHandlerContainer handlerContainer = (MHandlerContainer) origin;
			IEclipseContext castedContext = (IEclipseContext) context;
			for (MHandler mHandler : handlerContainer.getHandlers()) {
				processActiveHandler(mHandler, castedContext);
			}

		}
