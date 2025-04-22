		if (columnIndex == 0) {
			final FileDiff c = (FileDiff) element;
			switch (c.getChange()) {
			case ADD:
				return ADD;
			case COPY:
				return COPY;
			case DELETE:
				return DELETE;
			case RENAME:
			case MODIFY:
				return DEFAULT;
			}
		}
