		if (contentLength == null) {
			return -1;
		}

		try {
			long l = Long.parseLong(contentLength.getValue());
			if (l > Integer.MAX_VALUE) {
				return -1;
			}
			return (int) l;
		} catch (NumberFormatException e) {
			return -1;
		}
