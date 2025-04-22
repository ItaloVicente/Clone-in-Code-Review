	private void removeDescriptor(List descriptors, IThemeElementDefinition element) {
		String id = element.getId();
		for (int i = 0; i < descriptors.size(); i++) {
			IThemeElementDefinition existingElement = (IThemeElementDefinition) descriptors.get(i);
			if (existingElement.getId().equals(id)) {
				descriptors.remove(i);
			}
		}
	}

