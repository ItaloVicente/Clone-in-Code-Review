		if (propertyType.isAssignableFrom(Color.class)) {
			Color result = targetTheme.getColorRegistry().get(propertyId);
			if (result != null) {
				return result;
			}
		}
