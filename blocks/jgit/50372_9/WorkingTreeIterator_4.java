
	public String getFilterCommand(String filterCommandType)
			throws IOException {
		Map<String
		if (state.walk != null) {
			attributes = state.walk.getAttributes();
		} else {
			attributes = Collections.emptyMap();
		}

		Attribute filter = attributes.get(Constants.ATTR_FILTER);
		if (filter == null) {
			return null;
		}
		String filterValue = filter.getValue();
		if (filterValue == null) {
			return null;
		}

		String filterCommand = getFilterCommandDefinition(filterValue
				filterCommandType);
		if (filterCommand == null) {
			return null;
		}
		return filterCommand.replaceAll("%f"
				QuotedString.BOURNE.quote(getEntryPathString()));
	}

	private String getFilterCommandDefinition(String filterDriverName
			String filterCommandType) {
		String filterCommand = filterCommandsByNameDotType.get(key);
		if (filterCommand != null)
			return filterCommand;
		if (config == null) {
			if (repository == null) {
				return null;
			}
			config=repository.getConfig();
		}
		filterCommand = config.getString(Constants.ATTR_FILTER
				filterDriverName
				filterCommandType);
		if (filterCommand != null)
			filterCommandsByNameDotType.put(key
		return filterCommand;
	}

	public String getCleanFilterCommand() throws IOException {
		if (cleanFilterCommand == null)
			cleanFilterCommand = getFilterCommand(Constants.ATTR_FILTER_TYPE_CLEAN);
		return cleanFilterCommand;
	}
