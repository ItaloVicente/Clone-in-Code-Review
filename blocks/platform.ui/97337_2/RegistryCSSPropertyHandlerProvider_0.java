	private class LazyPropertyHander implements ICSSPropertyHandler {
		IConfigurationElement ce;
		ICSSPropertyHandler handler = null;

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
			return handler.applyCSSProperty(element, property, value, pseudo, engine);
		}

		private void load() {
			try {
				Object t = ce.createExecutableExtension(ATTR_HANDLER);

				if (t instanceof ICSSPropertyHandler) {
					handler = (ICSSPropertyHandler) t;
				} else {
					logError("invalid property handler for " + name);
				}
			} catch (CoreException e1) {
				logError("invalid property handler for " + name + ": " + e1);
			}
		}
	}

