		ModifyListener modifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateMessage();
			}
		};
		authorText.addModifyListener(modifyListener);
		committerText.addModifyListener(modifyListener);

