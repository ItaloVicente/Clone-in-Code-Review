                    .addPropertyChangeListener(new IPropertyChangeListener() {
                        @Override
						public void propertyChange(PropertyChangeEvent event) {
                            if (event.getProperty().equals(IAction.ENABLED)) {
                                Object val = event.getNewValue();
                                if (val instanceof Boolean) {
                                    setEnabled(((Boolean) val).booleanValue());
                                }
                            } else if (event.getProperty().equals(
                                    IAction.CHECKED)) {
                                Object val = event.getNewValue();
                                if (val instanceof Boolean) {
                                    setChecked(((Boolean) val).booleanValue());
                                }
                            } else if (event.getProperty().equals(IAction.TEXT)) {
                                Object val = event.getNewValue();
                                if (val instanceof String) {
                                    setText((String) val);
                                }
                            } else if (event.getProperty().equals(
                                    IAction.TOOL_TIP_TEXT)) {
                                Object val = event.getNewValue();
                                if (val instanceof String) {
                                    setToolTipText((String) val);
                                }
                            }
                        }
                    });
