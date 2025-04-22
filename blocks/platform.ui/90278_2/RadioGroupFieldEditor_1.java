                radio.addSelectionListener(widgetSelectedAdapter(event -> {
				    String oldValue = value;
				    value = (String) event.widget.getData();
				    setPresentsDefaultValue(false);
				    fireValueChanged(VALUE, oldValue, value);
				}));
