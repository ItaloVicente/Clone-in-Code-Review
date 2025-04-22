		if (value instanceof Boolean) {
			return key + "=" + ((Boolean) value).toString();
		} else if (value instanceof Integer) {
			return key + "=" + ((Integer) value).toString();
		} else {
			return key + "=\"" + value + "\"";
