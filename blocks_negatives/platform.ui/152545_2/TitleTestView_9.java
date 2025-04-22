		name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setPartName(name.getText());
			}
		});
