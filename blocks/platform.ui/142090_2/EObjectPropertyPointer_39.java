	protected synchronized EStructuralFeature[] getPropertyDescriptors() {
		if (propertyDescriptors == null) {
			propertyDescriptors = beanInfo.getPropertyDescriptors();
		}
		return propertyDescriptors;
	}
