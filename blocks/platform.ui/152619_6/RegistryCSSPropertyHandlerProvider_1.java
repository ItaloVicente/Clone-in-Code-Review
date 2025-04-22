					switch (handlers.size()) {
					case 0:
						handlers = propertyHandlerInstanceMap.computeIfAbsent(handler,
								h -> Collections.singletonList(h));
						break;
					case 1:
						handlers = new ArrayList<>(handlers);
					default:
						handlers.add(handler);
					}
