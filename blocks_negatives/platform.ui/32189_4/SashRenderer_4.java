
		try {
			int value = Integer.parseInt(info);
			return value;
		} catch (NumberFormatException e) {
			return UNDEFINED_WEIGHT;
		}
