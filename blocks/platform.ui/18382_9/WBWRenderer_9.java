	@SuppressWarnings("restriction")
	protected static class ThemeDefinitionChangedHandler implements
			EventHandler {
		public void handleEvent(Event event) {
			Object element = event.getProperty(IEventBroker.DATA);

			if (!(element instanceof MApplication)) {
				return;
			}

			List<Object> unusedResources = new ArrayList<Object>();
			Set<CSSEngine> engines = new HashSet<CSSEngine>();

			for (MWindow window : ((MApplication) element).getChildren()) {
				CSSEngine engine = getEngine(window);
				if (engine != null) {
					engines.add(engine);
				}
			}

			for (CSSEngine engine : engines) {
				unusedResources.addAll(removeResources(engine
						.getResourcesRegistry()));
				engine.reapply();
			}

			for (Object resource : unusedResources) {
				disposeResource(resource);
			}
		}

		protected CSSEngine getEngine(MWindow window) {
			return WidgetElement.getEngine((Widget) window.getWidget());
		}

		protected List<Object> removeResources(IResourcesRegistry registry) {
			if (registry instanceof SWTResourcesRegistry) {
				return ((SWTResourcesRegistry) registry)
						.removeResourcesByKeyTypeAndType(
								ResourceByDefinitionKey.class, Font.class,
								Color.class);
			}
			return Collections.emptyList();
		}

		protected void disposeResource(Object resource) {
			if (resource instanceof Resource
					&& !((Resource) resource).isDisposed()) {
				((Resource) resource).dispose();
			}
		}
	}
