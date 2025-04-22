		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (input!=null)
					input.setText(text.getText());
			}
