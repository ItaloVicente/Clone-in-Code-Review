            changeButton.addSelectionListener(widgetSelectedAdapter(evt -> {
			    String newValue = changePressed();
			    if (newValue != null) {
			        setStringValue(newValue);
			    }
			}));
