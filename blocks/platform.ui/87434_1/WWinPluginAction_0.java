                    .addPropertyChangeListener(event -> {
					    if (event.getProperty().equals(IAction.ENABLED)) {
					        Object val1 = event.getNewValue();
					        if (val1 instanceof Boolean) {
					            setEnabled(((Boolean) val1).booleanValue());
					        }
					    } else if (event.getProperty().equals(
					            IAction.CHECKED)) {
					        Object val2 = event.getNewValue();
					        if (val2 instanceof Boolean) {
					            setChecked(((Boolean) val2).booleanValue());
					        }
					    } else if (event.getProperty().equals(IAction.TEXT)) {
					        Object val3 = event.getNewValue();
					        if (val3 instanceof String) {
					            setText((String) val3);
					        }
					    } else if (event.getProperty().equals(
					            IAction.TOOL_TIP_TEXT)) {
					        Object val4 = event.getNewValue();
					        if (val4 instanceof String) {
					            setToolTipText((String) val4);
					        }
					    }
					});
