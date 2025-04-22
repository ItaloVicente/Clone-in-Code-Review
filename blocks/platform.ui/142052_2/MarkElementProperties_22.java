		if (name.equals(PROPERTY_LINECOUNT))
			return Integer.valueOf(element.getNumberOfLines());
		if (name.equals(PROPERTY_START))
			return Integer.valueOf(element.getStart());
		if (name.equals(PROPERTY_LENGTH))
			return Integer.valueOf(element.getLength());
		return null;
	}
