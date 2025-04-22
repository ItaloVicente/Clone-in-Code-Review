					}
					ContextManager contextManager = context.get(ContextManager.class);
					if (contextManager != null) {
						Set<?> activeContextIds = contextManager.getActiveContextIds();
						if (activeContextIds != null && !activeContextIds.isEmpty()) {
							StringBuilder sb = new StringBuilder("\n\tAll active contexts: "); //$NON-NLS-1$
							sb.append(activeContextIds);
							logger.trace(sb.toString());
