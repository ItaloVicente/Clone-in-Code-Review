		String n = null;
		if (value instanceof ConfigEnum) {
			n = ((ConfigEnum) value).toConfigValue();
		} else {
			n = value.name().toLowerCase().replace('_'
		}
