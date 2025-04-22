					Map<String, ICSSPropertyHandler> adaptersMap = handlersMap.get(adapter);
					if (adaptersMap == null) {
						handlersMap.put(adapter, adaptersMap = new HashMap<>());
					}
					if (!adaptersMap.containsKey(name)) {
						LazyPropertyHander lazyHandler = new LazyPropertyHander(name, ce);
						for (int i = 0; i < names.length; i++) {
							if (deprecated[i] == null) {
								DeprecatedPropertyHandlerWrapper deprecatedPropertyHandlerWrapper = new DeprecatedPropertyHandlerWrapper(
										lazyHandler, deprecated[i]);
								adaptersMap.put(names[i], deprecatedPropertyHandlerWrapper);
