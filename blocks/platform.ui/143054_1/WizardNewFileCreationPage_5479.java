		linkedResourceGroup = new CreateLinkedResourceGroup(IResource.FILE, e -> {
			setPageComplete(validatePage());
			firstLinkCheck = false;
		}, new CreateLinkedResourceGroup.IStringValue() {
			@Override
			public void setValue(String string) {
				resourceGroup.setResource(string);
			}
