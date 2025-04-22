			enablementListener = new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty() == PROP_ENABLED) {
						setProxyEnabled(event.getNewValue() == null ? false
								: ((Boolean) event.getNewValue())
										.booleanValue());
						fireHandlerChanged(new HandlerEvent(HandlerProxy.this,
								true, false));
					}
