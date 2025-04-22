		if (handlerListeners != null) {
			for (int i = 0; i < handlerListeners.size(); i++) {
				handlerListeners.get(i).handlerChanged(handlerEvent);
			}
		}

		if (super.hasListeners()) {
			final boolean enabledChanged;
			final boolean handledChanged;
			if (handlerEvent.haveAttributeValuesByNameChanged()) {
				Map previousAttributes = handlerEvent.getPreviousAttributeValuesByName();

				Object attribute = previousAttributes.get("enabled"); //$NON-NLS-1$
				if (attribute instanceof Boolean) {
					enabledChanged = ((Boolean) attribute).booleanValue();
				} else {
					enabledChanged = false;
				}

				attribute = previousAttributes.get(IHandlerAttributes.ATTRIBUTE_HANDLED);
				if (attribute instanceof Boolean) {
					handledChanged = ((Boolean) attribute).booleanValue();
				} else {
					handledChanged = false;
				}
			} else {
				enabledChanged = false;
				handledChanged = true;
