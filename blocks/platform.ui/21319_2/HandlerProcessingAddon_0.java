	@Inject
	public void handleHandlerEvent(
			@Optional @EventTopic(UIEvents.HandlerContainer.TOPIC_HANDLERS) Event event) {
		if (event == null)
			return;
		if ((event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MHandlerContainer)
				&& (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MContext)) {
			MHandlerContainer handlerContainer = (MHandlerContainer) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			if (UIEvents.EventTypes.ADD.equals(event.getProperty(UIEvents.EventTags.TYPE))) {
				if (event.getProperty(UIEvents.EventTags.NEW_VALUE) instanceof MHandler) {
					MHandler handler = (MHandler) event.getProperty(UIEvents.EventTags.NEW_VALUE);
					MContext mContext = (MContext) handlerContainer;
					IEclipseContext context = mContext.getContext();
					if (context != null) {
						processActiveHandler(handler, context);
					}
				}
			} else if (UIEvents.EventTypes.REMOVE
					.equals(event.getProperty(UIEvents.EventTags.TYPE))) {
				if (event.getProperty(UIEvents.EventTags.OLD_VALUE) instanceof MHandler) {
					MHandler handler = (MHandler) event.getProperty(UIEvents.EventTags.OLD_VALUE);
					MContext mContext = (MContext) handlerContainer;
					IEclipseContext context = mContext.getContext();
					if (context != null) {
						MCommand command = handler.getCommand();
						if (command != null) {
							String commandId = command.getElementId();
							EHandlerService handlerService = (EHandlerService) context
									.get(EHandlerService.class.getName());
							handlerService.deactivateHandler(commandId, handler.getObject());
