					try {
						Map<String, ICSSPropertyHandler> adaptersMap = handlersMap
								.get(adapter);
						if (adaptersMap == null) {
							handlersMap
									.put(adapter,
											adaptersMap = new HashMap<>());
						}
						if (!adaptersMap.containsKey(name)) {
							Object t = ce
									.createExecutableExtension(ATTR_HANDLER);
							if (t instanceof ICSSPropertyHandler) {
								for (int i = 0; i < names.length; i++) {
									adaptersMap
											.put(names[i],
													deprecated[i] == null ? (ICSSPropertyHandler) t
															: new DeprecatedPropertyHandlerWrapper(
																	(ICSSPropertyHandler) t,
																	deprecated[i]));
								}
