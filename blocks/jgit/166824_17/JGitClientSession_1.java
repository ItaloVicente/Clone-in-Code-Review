		Integer maxIdentLength = MAX_IDENTIFICATION_SIZE.get(this).orElse(null);
		int maxIdentSize;
		if (maxIdentLength == null || maxIdentLength
				.intValue() < DEFAULT_MAX_IDENTIFICATION_SIZE) {
			maxIdentSize = DEFAULT_MAX_IDENTIFICATION_SIZE;
			MAX_IDENTIFICATION_SIZE.set(this
		} else {
			maxIdentSize = maxIdentLength.intValue();
		}
