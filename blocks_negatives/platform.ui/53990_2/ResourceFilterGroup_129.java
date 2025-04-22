		arguments.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				FilterTypeUtil.setValue(filter, FilterTypeUtil.ARGUMENTS,
						arguments.getText());
			}
		});
