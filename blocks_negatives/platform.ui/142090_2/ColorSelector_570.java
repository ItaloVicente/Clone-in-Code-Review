		    RGB oldValue = fColorValue;
		    fColorValue = newColor;
		    final Object[] finalListeners = getListeners();
		    if (finalListeners.length > 0) {
		        PropertyChangeEvent pEvent = new PropertyChangeEvent(
		                this, PROP_COLORCHANGE, oldValue, newColor);
		        for (Object finalListener : finalListeners) {
		            IPropertyChangeListener listener = (IPropertyChangeListener) finalListener;
		            listener.propertyChange(pEvent);
		        }
		    }
		    updateColorImage();
