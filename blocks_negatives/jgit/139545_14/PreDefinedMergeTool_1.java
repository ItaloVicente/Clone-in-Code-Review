		if (path.contains(" ")) {
			if (!path.startsWith("\"")) {
				path = "\"" + path;
			}
			if (!path.endsWith("\"")) {
				path = path + "\"";
			}
		}
