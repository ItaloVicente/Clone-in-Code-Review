		browserNameTextfield = createText(composite, browser.getName(), new StringModifyListener() {
			@Override
			public void valueChanged(String s) {
				browser.setName(s);
				validateFields();
			}
