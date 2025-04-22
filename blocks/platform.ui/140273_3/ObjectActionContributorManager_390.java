		IConfigurationElement[] addedElements = addedExtension.getConfigurationElements();
		for (IConfigurationElement addedElement : addedElements) {
			ObjectActionContributorReader reader = new ObjectActionContributorReader();
			reader.setManager(this);
			reader.readElement(addedElement);
		}
	}
