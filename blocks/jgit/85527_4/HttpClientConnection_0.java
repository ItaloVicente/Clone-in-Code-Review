		if (contentLength == null) {
			return -1;
		}

		try {
			int l = Integer.parseInt(contentLength.getValue());
			return l < 0 ? -1 : l;
		} catch (NumberFormatException e) {
			return -1;
		}
