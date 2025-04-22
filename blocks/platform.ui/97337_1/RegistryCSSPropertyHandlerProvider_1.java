					Map<String, ICSSPropertyHandler> adaptersMap = handlersMap.get(adapter);
					if (adaptersMap == null) {
						handlersMap.put(adapter, adaptersMap = new HashMap<>());
					}
					if (!adaptersMap.containsKey(name)) {
						for (int i = 0; i < names.length; i++) {
							LazyPropertyHander lazyHandler = new LazyPropertyHander(name, ce);
							if (deprecated[i] == null) {
								DeprecatedPropertyHandlerWrapper deprecatedPropertyHandlerWrapper = new DeprecatedPropertyHandlerWrapper(
										lazyHandler, deprecated[i]);
								adaptersMap.put(names[i], deprecatedPropertyHandlerWrapper);
