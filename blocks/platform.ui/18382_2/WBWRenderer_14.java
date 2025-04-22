	@SuppressWarnings("restriction")
	protected static class ThemeDefinitionChangedHandler implements
			EventHandler {
		public void handleEvent(Event event) {
			Object element = event.getProperty(IEventBroker.DATA);

			if (!(element instanceof MApplication)) {
				return;
			}

			for (MWindow window : ((MApplication) element).getChildren()) {
				CSSEngine engine = getEngine(window);
				if (engine != null) {
					invalidateResourcesInRegistry(engine.getResourcesRegistry());
					engine.reapply();
				}
			}
		}

		protected CSSEngine getEngine(MWindow window) {
			return WidgetElement.getEngine((Widget) window.getWidget());
		}

		private void invalidateResourcesInRegistry(IResourcesRegistry registry) {
			if (registry instanceof SWTResourcesRegistry) {
				((SWTResourcesRegistry) registry).invalidateResources(
						Font.class, Color.class);
			}
		}

	}

