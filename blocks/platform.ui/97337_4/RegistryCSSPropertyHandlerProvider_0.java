	private class LazyPropertyHander implements ICSSPropertyHandler, ICSSPropertyHandler2 {
		IConfigurationElement ce;
		Object handler = null;

		boolean loaded = false;
		private String name;

		public LazyPropertyHander(String name, IConfigurationElement ce) {
			this.name = name;
			this.ce = ce;
		}

		@Override
		public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo,
				CSSEngine engine) throws Exception {
			if (!loaded) {
				load();
			}
			if (handler == null) {
				return false;
			}
			return ((ICSSPropertyHandler) handler).applyCSSProperty(element, property, value, pseudo, engine);
		}

		@Override
		public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine)
				throws Exception {
			if (!loaded) {
				load();
			}
			if (handler == null) {
				return null;
			}
			return ((ICSSPropertyHandler) handler).retrieveCSSProperty(element, property, pseudo, engine);
		}

		@Override
		public void onAllCSSPropertiesApplyed(Object element, CSSEngine engine) throws Exception {
			if (!loaded) {
				load();
			}
			if (handler == null) {
				return;
			}
			if (handler instanceof ICSSPropertyHandler2) {
				ICSSPropertyHandler2 handler2 = (ICSSPropertyHandler2) handler;
				handler2.onAllCSSPropertiesApplyed(element, engine);
			}
		}

		@Override
		public void onAllCSSPropertiesApplyed(Object element, CSSEngine engine, String pseudo) throws Exception {
			if (!loaded) {
				load();
			}
			if (handler == null) {
				return;
			}
			if (handler instanceof ICSSPropertyHandler2) {
				ICSSPropertyHandler2 handler2 = (ICSSPropertyHandler2) handler;
				handler2.onAllCSSPropertiesApplyed(element, engine, pseudo);
			}
		}

		private void load() {
			try {
				Object t = ce.createExecutableExtension(ATTR_HANDLER);

				if (t instanceof ICSSPropertyHandler) {
					handler = t;
				} else {
					logError("invalid property handler for " + name);
				}
			} catch (CoreException e1) {
				logError("invalid property handler for " + name + ": " + e1);
			}
		}
	}

