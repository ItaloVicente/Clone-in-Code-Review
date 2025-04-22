		browserParametersTextfield = createText(composite, browser.getParameters(), new StringModifyListener() {
			@Override
			public void valueChanged(String s) {
				browser.setParameters(s);
			}
		}, true);
