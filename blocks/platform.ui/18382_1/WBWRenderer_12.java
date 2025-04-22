	@SuppressWarnings("restriction")
	protected static class ThemeDefinitionChangedHandler implements
			EventHandler {
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			Object eventType = event.getProperty(UIEvents.EventTypes.ADD_MANY);
			boolean partialThemeChanges = eventType != null
					&& (Boolean) eventType;

			if (!(element instanceof MApplication)) {
				return;
			}

			for (MWindow window : ((MApplication) element).getChildren()) {
				CSSEngine engine = getEngine(window);
				if (engine != null) {
					if (!partialThemeChanges) {
						disposeUnusedResources(engine.getResourcesRegistry());
					}
					invalidateResourcesInRegistry(engine.getResourcesRegistry());
					engine.reapply();
				}
			}
		}

		protected CSSEngine getEngine(MWindow window) {
			return WidgetElement.getEngine((Widget) window.getWidget());
		}

		private void disposeUnusedResources(IResourcesRegistry registry) {
			if (registry instanceof SWTResourcesRegistry) {
				((SWTResourcesRegistry) registry).disposeUnusedResources();
			}
		}

		private void invalidateResourcesInRegistry(IResourcesRegistry registry) {
			if (registry instanceof SWTResourcesRegistry) {
				((SWTResourcesRegistry) registry).invalidateResources(
						Font.class, Color.class);
			}
		}

	}

