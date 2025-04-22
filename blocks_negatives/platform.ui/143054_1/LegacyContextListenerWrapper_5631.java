		wrappedListener
				.contextChanged(new org.eclipse.ui.contexts.ContextEvent(
						new ContextLegacyWrapper(contextEvent.getContext(),
								contextManager), contextEvent
								.isDefinedChanged(), false, contextEvent
								.isNameChanged(), contextEvent
								.isParentIdChanged()));
