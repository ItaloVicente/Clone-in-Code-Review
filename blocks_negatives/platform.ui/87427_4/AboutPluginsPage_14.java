		filterText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				searchFilter.setPattern(filterText.getText());
				vendorInfo.refresh();
			}
