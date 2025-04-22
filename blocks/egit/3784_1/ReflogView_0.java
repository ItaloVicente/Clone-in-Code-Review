		searchBox.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				reflogTableViewer.refresh();
			}
		});
