		handler = newHandler;
		if (handler == null) {
			setEnabled(false);
			if (getStyle() == AS_CHECK_BOX || getStyle() == AS_RADIO_BUTTON) {
				setChecked(false);
			}
		} else {
			setEnabled(handler.isEnabled());
			if (getStyle() == AS_CHECK_BOX || getStyle() == AS_RADIO_BUTTON) {
				setChecked(handler.isChecked());
			}
			handler.addPropertyChangeListener(propertyChangeListener);
		}
