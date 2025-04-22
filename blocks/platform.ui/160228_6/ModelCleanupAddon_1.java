	private List<MHandler> getObsoleteHandlers(List<MHandler> handlers) {
		for (Iterator<MHandler> iterator = handlers.iterator(); iterator.hasNext();) {
			MHandler appElement = iterator.next();
			boolean validAppElement = isValidHandler(appElement);
			if (validAppElement) {
				iterator.remove();
			} else {
				logMissingClassWarning(appElement);
			}
		}

		return handlers;
	}

