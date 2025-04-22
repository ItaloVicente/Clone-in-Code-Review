		linkedResourceGroup = new CreateLinkedResourceGroup(IResource.FOLDER, e -> {
			setPageComplete(validatePage());
			firstLinkCheck = false;
		}, new CreateLinkedResourceGroup.IStringValue() {
			@Override
			public String getValue() {
				return resourceGroup.getResource();
			}
