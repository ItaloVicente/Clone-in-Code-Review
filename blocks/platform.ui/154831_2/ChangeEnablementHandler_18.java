			fContextManagerListener = contextManagerEvent -> {
if (contextManagerEvent.isActiveContextsChanged()) {
			setEnabled(contextManagerEvent.getContextManager()
					.getActiveContextIds().contains(CONTEXT_ID));
}
};
