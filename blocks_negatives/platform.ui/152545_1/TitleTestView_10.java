		contentDescription.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setContentDescription(contentDescription.getText());
			}
		});
