
		context = (IEclipseContext) Workbench.getInstance().getService(IEclipseContext.class);
		eventBroker = (IEventBroker) Workbench.getInstance().getService(IEventBroker.class);
		if (eventBroker != null) {
			eventBroker.subscribe(UIEvents.UILifeCycle.THEME_CHANGED, themeChangedHandler);
		}
