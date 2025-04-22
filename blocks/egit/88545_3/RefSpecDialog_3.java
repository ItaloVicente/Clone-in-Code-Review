		specString.addModifyListener(event -> {
			if (!specString.isFocusControl()
					|| getSpec().toString().equals(specString.getText())) {
				return;
			}
			try {
