		String string = configurationElement.getAttribute(key);
		if (string == null) {
			return null;
		}
		return Boolean.valueOf(string);
	}

	@Override
