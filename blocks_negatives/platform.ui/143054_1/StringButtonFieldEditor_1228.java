            changeButton.setText(changeButtonText);
            changeButton.setFont(parent.getFont());
            changeButton.addSelectionListener(widgetSelectedAdapter(evt -> {
			    String newValue = changePressed();
			    if (newValue != null) {
			        setStringValue(newValue);
			    }
