		return descriptors[idx];
	}

	private IThemeElementDefinition addOrReplaceDescriptor(List descriptors, IThemeElementDefinition newElement) {
		String id = newElement.getId();
		for (int i = 0; i < descriptors.size(); i++) {
			IThemeElementDefinition existingElement = (IThemeElementDefinition) descriptors.get(i);
			if (existingElement.getId().equals(id)) {
				descriptors.remove(i);
				descriptors.add(newElement);
				return existingElement;
			}
