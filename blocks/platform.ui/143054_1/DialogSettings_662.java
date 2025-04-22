		String setting = items.get(key);
		if (setting == null) {
			throw new NumberFormatException(
					"There is no setting associated with the key \"" + key + "\"");//$NON-NLS-1$ //$NON-NLS-2$
		}

		return Long.parseLong(setting);
	}

	@Override
