
	public TemporaryBuffer processFilter(String filterCommand
			InputStream in) throws FilterFailedException {
		if (repository == null)
			return null;
		TemporaryBuffer outBuffer = new TemporaryBuffer.LocalFile(
				repository.getDirectory());
		TemporaryBuffer errorBuffer = new TemporaryBuffer.Heap(1024
				1024 * 1024);
		int rc;

		try {
			FS fs = repository.getFS();
			ProcessBuilder filterProcessBuilder = fs.runInShell(
					filterCommand
			rc = fs.runProcess(filterProcessBuilder
					errorBuffer
			if (rc != 0) {
				throw new FilterFailedException(rc
						outBuffer
			}
			return outBuffer;
		} catch (IOException | InterruptedException e) {
			throw new FilterFailedException(e
					errorBuffer);
		} finally {
			errorBuffer.destroy();
		}
	}

	public String getFilterCommand(String filterCommandType)
			throws IOException {
		if (repository == null)
			return null;
		Map<String
		AttributesNode entryAttributesNode = getEntryAttributesNode();
		if (entryAttributesNode == null)
			return null;
		entryAttributesNode.getAttributes(getEntryPathString()
				attributes);
		Attribute filter = attributes.get(Constants.ATTR_FILTER);
		if (filter == null || filter.getValue() == null) {
			return null;
		}
		String filterCommand = getFilterCommandDefinition(filter.getValue()
				filterCommandType);
		if (filterCommand == null) {
			return null;
		}
		return filterCommand.replaceAll(
				"%f"
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
