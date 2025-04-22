					switch (handlers2.size()) {
					case 0:
						handlers2 = propertyHandler2InstanceMap.computeIfAbsent(propertyHandler2,
								h -> Collections.singletonList(h));
						break;
					case 1:
						handlers2 = new ArrayList<>(handlers2);
