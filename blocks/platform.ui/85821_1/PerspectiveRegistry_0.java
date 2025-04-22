		String trimE4 = label.trim();
		String trimE3 = label.replace(' ', '_').trim();
		if (id.endsWith(label)) {
			index = id.lastIndexOf(label) - 1;
		} else if (id.endsWith(trimE4)) {
			index = id.lastIndexOf(trimE4) - 1;
		} else if (id.endsWith(trimE3)) {
			index = id.lastIndexOf(trimE3) - 1;
		}
		if (index >= 0 && index < id.length()) {
			return id.substring(0, index);
		}
		return id;
