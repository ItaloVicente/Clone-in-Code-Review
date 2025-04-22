		final boolean enabledChanged = ((Boolean) event
				.getPreviousAttributeValuesByName().get(
						ILegacyAttributeNames.ENABLED)).booleanValue() != handler
				.isEnabled();
		final boolean handledChanged = ((Boolean) event
				.getPreviousAttributeValuesByName().get(
						ILegacyAttributeNames.HANDLED)).booleanValue() != handler
				.isHandled();
		listener.handlerChanged(new org.eclipse.core.commands.HandlerEvent(
				handler, enabledChanged, handledChanged));
