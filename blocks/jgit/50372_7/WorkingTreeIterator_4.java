
	public String getFilterCommand(String filterCommandType)
			throws IOException {
		Set<Attribute> attributes;
		if (state.walk != null) {
			attributes = state.walk.getAttributes();
		} else {
			attributes = new HashSet<Attribute>();
		}

		String filter = getAttribute(attributes
		if (filter == null) {
			return null;
		}
		String filterCommand = getFilterCommandDefinition(filter
				filterCommandType);
		if (filterCommand == null) {
			return null;
		}
		return filterCommand.replaceAll("%f"
				QuotedString.BOURNE.quote(getEntryPathString()));
	}

	private String getAttribute(Collection<Attribute> attributes
		for (Attribute a : attributes) {
			if (key.equals(a.getKey())) {
				return a.getValue();
			}
		}
		return null;
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
