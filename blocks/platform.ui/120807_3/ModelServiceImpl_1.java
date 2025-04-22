
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		if (bundle != null && bundle.getBundleContext() != null) {
			Dictionary<String, Object> properties = new Hashtable<>();
			properties.put(EventConstants.EVENT_TOPIC, new String[] { UIEvents.UIElement.TOPIC_WIDGET });
			bundle.getBundleContext().registerService(EventHandler.class.getName(),
						ContextInjectionFactory.make(HostedElementEventHandler.class, appContext), properties);
		} else {
			IEventBroker eventBroker = appContext.get(IEventBroker.class);
			if (eventBroker == null) {
				throw new IllegalStateException(
						"Could not get an IEventBroker instance. Please check your configuration that a providing bundle is present and active."); //$NON-NLS-1$
			}
			eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, null,
					ContextInjectionFactory.make(HostedElementEventHandler.class, appContext), true);
