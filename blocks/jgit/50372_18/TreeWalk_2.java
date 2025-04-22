
	public String getFilterCommand(String filterCommandType)
			throws IOException {
		Map<String

		Attribute f = attributes.get(Constants.ATTR_FILTER);
		if (f == null) {
			return null;
		}
		String filterValue = f.getValue();
		if (filterValue == null) {
			return null;
		}

		String filterCommand = getFilterCommandDefinition(filterValue
				filterCommandType);
		if (filterCommand == null) {
			return null;
		}
		return filterCommand.replaceAll("%f"
				QuotedString.BOURNE.quote((getPathString())));
	}

	private String getFilterCommandDefinition(String filterDriverName
			String filterCommandType) {
		String filterCommand = filterCommandsByNameDotType.get(key);
		if (filterCommand != null)
			return filterCommand;
		filterCommand = config.getString(Constants.ATTR_FILTER
				filterDriverName
		if (filterCommand != null)
			filterCommandsByNameDotType.put(key
		return filterCommand;
	}
