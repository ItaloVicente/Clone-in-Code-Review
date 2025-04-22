		ParseableSimpleDateFormat[] values = ParseableSimpleDateFormat.values();
		StringBuilder allFormats = new StringBuilder("\"")
				.append(values[0].formatStr);
		for (int i = 1; i < values.length; i++)
			allFormats.append("\"
		allFormats.append("\"");
		throw new ParseException(MessageFormat.format(
				JGitText.get().cannotParseDate
