		InputStream xstream = getStream(getxName(name, zoom));
		if (xstream != null) {
			return xstream;
		}

		InputStream xpath = getStream(getxPath(name, zoom));
		if (xpath != null) {
			return xpath;
		}

		return null;
	}
