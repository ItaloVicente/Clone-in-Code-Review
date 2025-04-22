
		ModifyListener validator = new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				updateMessage();
			}
		};
		commitText.getTextWidget().addModifyListener(validator);
		authorText.addModifyListener(validator);
		committerText.addModifyListener(validator);
		filesViewer.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				updateMessage();
			}
		});

