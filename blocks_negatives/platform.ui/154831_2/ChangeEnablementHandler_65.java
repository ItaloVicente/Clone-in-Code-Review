			fContextManagerListener = new IContextManagerListener() {
				@Override
				public void contextManagerChanged(
						ContextManagerEvent contextManagerEvent) {
					if (contextManagerEvent.isActiveContextsChanged()) {
						setEnabled(contextManagerEvent.getContextManager()
								.getActiveContextIds().contains(CONTEXT_ID));
					}
				}
			};
