			propertyChangeListener = propertyChangeEvent -> {
final String property = propertyChangeEvent.getProperty();
fireHandlerChanged(new HandlerEvent(ActionHandler.this,
				IAction.ENABLED.equals(property),
				IAction.HANDLED.equals(property)));
};
