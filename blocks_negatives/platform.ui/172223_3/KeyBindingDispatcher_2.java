						ContextManager contextManager = context.get(ContextManager.class);
						if (contextManager != null) {
							Set<?> activeContextIds = contextManager.getActiveContextIds();
							if (activeContextIds != null && !activeContextIds.isEmpty()) {
								sb.append(activeContextIds);
								logger.trace(sb.toString());
							}
