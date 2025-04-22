	private boolean anyVisibleWhen(MToolBarElement toolBarElement) {
		if (toolBarElement.getVisibleWhen() != null
				|| toolBarElement.getPersistedState().get(MenuManagerRenderer.VISIBILITY_IDENTIFIER) != null) {
			return true;
		}
		return false;
	}

