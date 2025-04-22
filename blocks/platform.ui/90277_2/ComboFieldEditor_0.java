			fCombo.addSelectionListener(widgetSelectedAdapter(evt -> {
				String oldValue = fValue;
				String name = fCombo.getText();
				fValue = getValueForName(name);
				setPresentsDefaultValue(false);
				fireValueChanged(VALUE, oldValue, fValue);
			}));
