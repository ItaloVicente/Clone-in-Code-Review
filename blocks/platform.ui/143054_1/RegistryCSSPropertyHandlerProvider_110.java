	private void registerPropertyHandler(Map<String, Map<String, ICSSPropertyHandler>> handlersMap,
			IConfigurationElement ce) {
		String name = ce.getAttribute(ATTR_COMPOSITE);
		String adapter = ce.getAttribute(ATTR_ADAPTER);
		IConfigurationElement[] children = ce.getChildren();
		String[] names = new String[children.length];
		String[] deprecated = new String[children.length];
		for (int i = 0; i < children.length; i++) {
			if (children[i].getName().equals(ATTR_PROPERTY_NAME)) {
				names[i] = children[i].getAttribute(ATTR_NAME);
				deprecated[i] = children[i].getAttribute(ATTR_DEPRECATED);
				if (deprecated[i] != null) {
					hasDeprecatedProperties = true;
				}
			}

		}
		try {
			Map<String, ICSSPropertyHandler> adaptersMap = handlersMap.get(adapter);
			if (adaptersMap == null) {
				handlersMap.put(adapter, adaptersMap = new HashMap<>());
			}
			if (!adaptersMap.containsKey(name)) {
				Object t = ce.createExecutableExtension(ATTR_HANDLER);
				if (t instanceof ICSSPropertyHandler) {
					for (int i = 0; i < names.length; i++) {
						adaptersMap.put(names[i],
								deprecated[i] == null ? (ICSSPropertyHandler) t
										: new DeprecatedPropertyHandlerWrapper(
												(ICSSPropertyHandler) t,
												deprecated[i]));
					}
				} else {
					logError("invalid property handler for " + name);
				}
			}
		} catch (CoreException e1) {
			logError("invalid property handler for " + name + ": "
					+ e1);
		}
	}

