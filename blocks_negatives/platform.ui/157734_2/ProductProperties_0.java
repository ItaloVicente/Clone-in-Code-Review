		String property = product.getProperty(prop);
		if (property == null) {
		}
		if (property.indexOf('{') == -1) {
			return property;
		}
		return property;
