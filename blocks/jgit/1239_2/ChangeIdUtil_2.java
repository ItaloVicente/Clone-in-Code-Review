		return insertId(message
	}

	public static String insertId(String message
			boolean replaceExisting) {
		if (message.indexOf("Change-Id:") > 0) {
			if (replaceExisting) {
				int i = message.indexOf("Change-Id:") + 10;
				while (message.charAt(i) == ' ')
					i++;
				String oldId = message.length() == (i + 40) ?
						message.substring(i) : message.substring(i
				message = message.replace(oldId
			}
