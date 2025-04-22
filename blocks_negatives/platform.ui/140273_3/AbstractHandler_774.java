        }

        if (super.hasListeners()) {
            final boolean enabledChanged;
            final boolean handledChanged;
            if (handlerEvent.haveAttributeValuesByNameChanged()) {
                Map previousAttributes = handlerEvent
                        .getPreviousAttributeValuesByName();

                if (attribute instanceof Boolean) {
                    enabledChanged = ((Boolean) attribute).booleanValue();
                } else {
                    enabledChanged = false;
                }

                attribute = previousAttributes
                        .get(IHandlerAttributes.ATTRIBUTE_HANDLED);
                if (attribute instanceof Boolean) {
                    handledChanged = ((Boolean) attribute).booleanValue();
                } else {
                    handledChanged = false;
                }
            } else {
                enabledChanged = false;
                handledChanged = true;
            }
            final HandlerEvent newEvent = new HandlerEvent(this,
                    enabledChanged, handledChanged);
            super.fireHandlerChanged(newEvent);
        }
    }

    /**
     * This simply return an empty map. The default implementation has no
     * attributes.
     *
     * @see IHandler#getAttributeValuesByName()
     */
