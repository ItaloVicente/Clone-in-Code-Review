					Map<String, ICSSPropertyHandler> adaptersMap = handlersMap.get(adapter);
					if (adaptersMap == null) {
						handlersMap.put(adapter, adaptersMap = new HashMap<>());
					}
					if (!adaptersMap.containsKey(name)) {
						ICSSPropertyHandler handler = new LazyPropertyHander(name, ce);
						for (int i = 0; i < names.length; i++) {
							if (deprecated[i] != null) {
								handler = new DeprecatedPropertyHandlerWrapper(handler, deprecated[i]);
