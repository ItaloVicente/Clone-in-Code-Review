		for (String entry : splitList) {
			Matcher matcher = pattern.matcher(entry);
			if (matcher.find()) {
				return matcher.group(1);
			}
		}
