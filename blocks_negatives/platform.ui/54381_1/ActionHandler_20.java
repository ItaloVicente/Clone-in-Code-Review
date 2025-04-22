			propertyChangeListener = new IPropertyChangeListener() {
				@Override
				public final void propertyChange(
						final PropertyChangeEvent propertyChangeEvent) {
					final String property = propertyChangeEvent.getProperty();
					fireHandlerChanged(new HandlerEvent(ActionHandler.this,
							IAction.ENABLED.equals(property),
							IAction.HANDLED.equals(property)));
				}
			};
