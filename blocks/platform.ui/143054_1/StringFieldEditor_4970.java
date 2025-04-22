			oldValue = textField.getText();
			if (!oldValue.equals(value)) {
				textField.setText(value);
				valueChanged();
			}
		}
	}


	public void setTextLimit(int limit) {
		textLimit = limit;
		if (textField != null) {
