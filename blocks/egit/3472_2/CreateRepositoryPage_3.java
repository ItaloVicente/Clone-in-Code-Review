		if (!hideBare) {
			bareButton = new Button(main, SWT.CHECK);
			bareButton.setText(UIText.CreateRepositoryPage_BareCheckbox);
			GridDataFactory.fillDefaults().indent(10, 0).span(3, 1)
					.applyTo(bareButton);
			directoryText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					checkPage();
				}
			});
		}
