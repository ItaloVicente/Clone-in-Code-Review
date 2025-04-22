		protected void sendThemeRegistryRestyledEvent() {
			IEventBroker eventBroker = (IEventBroker) getContext()
					.get(IEventBroker.class.getName());
			eventBroker.send(Events.THEME_REGISTRY_RESTYLED, null);
		}

