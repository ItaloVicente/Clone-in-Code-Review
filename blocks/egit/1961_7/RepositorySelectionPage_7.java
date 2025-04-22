		passText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				setURI(uri.setPass(null));
				password = passText.getText();
			}
		});
