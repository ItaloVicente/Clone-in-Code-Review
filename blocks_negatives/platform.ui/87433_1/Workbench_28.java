				contextManager.addContextManagerListener(new IContextManagerListener() {
					@Override
					public void contextManagerChanged(ContextManagerEvent contextManagerEvent) {
						if (contextManagerEvent.isContextChanged()) {
							String id = contextManagerEvent.getContextId();
							if (id != null) {
								defineBindingTable(id);
							}
