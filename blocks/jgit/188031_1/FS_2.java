		w = w.substring(colon + 1);
		return w.isEmpty() ? null : new File(w);
	}

	private long parseVersion(String version) {
		Matcher m = VERSION.matcher(version);
		if (m.find()) {
			try {
				return makeVersion(
						Integer.parseInt(m.group(1))
						Integer.parseInt(m.group(2))
						Integer.parseInt(m.group(3)));
			} catch (NumberFormatException e) {
			}
		}
		return -1;
	}
