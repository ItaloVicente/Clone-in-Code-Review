		textFilter.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		textFilter.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkInput();
			}
		});
