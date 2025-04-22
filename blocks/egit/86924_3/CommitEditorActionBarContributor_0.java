			Map<?, ?> handlers = textEditorBars.getGlobalActionHandlers();
			if (handlers != null) {
				for (Map.Entry<?, ?> entry : handlers.entrySet()) {
					Object key = entry.getKey();
					Object value = entry.getValue();
					if (key instanceof String && value instanceof IAction) {
						rootBars.setGlobalActionHandler((String) key,
								(IAction) value);
					}
				}
			}
