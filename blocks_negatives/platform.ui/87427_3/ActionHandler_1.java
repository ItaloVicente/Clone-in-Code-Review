            propertyChangeListener = new IPropertyChangeListener() {
                @Override
				public void propertyChange(
                        PropertyChangeEvent propertyChangeEvent) {
                    String property = propertyChangeEvent.getProperty();
                    if (IAction.ENABLED.equals(property)
                            || IAction.CHECKED.equals(property)
                            || IHandlerAttributes.ATTRIBUTE_HANDLED
                                    .equals(property)) {
