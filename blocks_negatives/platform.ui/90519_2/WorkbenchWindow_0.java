			for (Iterator<Entry<String, ActionHandler>> iterator = handlersByCommandId.entrySet()
					.iterator(); iterator.hasNext();) {
				Entry<String, ActionHandler> entry = iterator.next();
				String commandId = entry.getKey();
				IHandler handler = entry.getValue();
				newHandlers.add(handlerService.activateHandler(commandId, handler, expression));
			}
