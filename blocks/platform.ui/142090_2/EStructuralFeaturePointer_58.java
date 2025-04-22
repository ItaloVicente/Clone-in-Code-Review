		JXPathContext context,
		QName name,
		int index,
		Object value) {
		EStructuralFeaturePointer prop = (EStructuralFeaturePointer) clone();
		if (name != null) {
			prop.setPropertyName(name.toString());
		}
		prop.setIndex(index);
		return prop.createPath(context, value);
	}

	@Override
