	public String getSmudgeCommand(int index)
			throws IOException {
		return getSmudgeCommand(getAttributes(index));
	}

	public String getSmudgeCommand(Attributes attributes) throws IOException {
		if (attributes == null) {
			return null;
		}
		Attribute f = attributes.get(Constants.ATTR_FILTER);
		if (f == null) {
			return null;
		}
		String filterValue = f.getValue();
		if (filterValue == null) {
			return null;
		}

		String filterCommand = getFilterCommandDefinition(filterValue
				Constants.ATTR_FILTER_TYPE_SMUDGE);
		if (filterCommand == null) {
			return null;
		}
		return filterCommand.replaceAll("%f"
				Matcher.quoteReplacement(
						QuotedString.BOURNE.quote((getPathString()))));
	}

