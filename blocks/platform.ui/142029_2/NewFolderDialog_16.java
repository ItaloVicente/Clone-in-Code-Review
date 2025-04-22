		linkedResourceGroup = new CreateLinkedResourceGroup(IResource.FOLDER, e -> {
			validateLinkedResource();
			firstLinkCheck = false;
		}, new CreateLinkedResourceGroup.IStringValue() {
			@Override
			public void setValue(String string) {
				folderNameField.setText(string);
			}

			@Override
			public String getValue() {
				return folderNameField.getText();
			}

			@Override
			public IResource getResource() {
				return container;
			}
		});
