		browserLocationTextfield = createText(composite, browser.getLocation(), new StringModifyListener() {
			@Override
			public void valueChanged(String s) {
				browser.setLocation(s);
				validateFields();
			}
