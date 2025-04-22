    protected void fireHandlerChanged(HandlerEvent handlerEvent) {
        super.fireHandlerChanged(handlerEvent);

        if (handlerListeners != null) {
            final boolean attributesChanged = handlerEvent.isEnabledChanged()
                    || handlerEvent.isHandledChanged();
            final Map previousAttributes;
            if (attributesChanged) {
                previousAttributes = new HashMap();
                previousAttributes.putAll(getAttributeValuesByName());
                if (handlerEvent.isEnabledChanged()) {
                	Boolean disabled = !isEnabled() ? Boolean.TRUE: Boolean.FALSE;
                    previousAttributes
                            .put("enabled", disabled); //$NON-NLS-1$
                }
                if (handlerEvent.isHandledChanged()) {
                	Boolean notHandled = !isHandled() ? Boolean.TRUE: Boolean.FALSE;
                    previousAttributes.put(
                            IHandlerAttributes.ATTRIBUTE_HANDLED, notHandled);
                }
            } else {
                previousAttributes = null;
            }
            final org.eclipse.ui.commands.HandlerEvent legacyEvent = new org.eclipse.ui.commands.HandlerEvent(
                    this, attributesChanged, previousAttributes);

            for (int i = 0; i < handlerListeners.size(); i++) {
                handlerListeners
                        .get(i).handlerChanged(legacyEvent);
            }
        }
    }
