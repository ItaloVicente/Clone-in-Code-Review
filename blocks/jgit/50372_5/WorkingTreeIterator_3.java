
	public TemporaryBuffer processFilter(String filterCommandType
			throws FilterFailedException {
		TemporaryBuffer outBuffer = null;
		TemporaryBuffer errorBuffer = null;
		String filterCommand = null;
		AttributesNode entryAttributesNode;
		Attribute filter;
		String p = null;
		int rc;

		try {
			if ((entryAttributesNode = getEntryAttributesNode()) == null) {
				return null;
			}
			p = getEntryPathString();
			Map<String
			entryAttributesNode.getAttributes(p
			filter = attributes.get(Constants.ATTR_FILTER);
			if (filter == null) {
				return null;
			}
			filterCommand = config.getString(Constants.ATTR_FILTER
					filter.getValue()
			if (filterCommand == null) {
				return null;
			}
			filterCommand = filterCommand.replaceAll("%f"
			ProcessBuilder filterProcessBuilder = repository.getFS()
					.runInShell(filterCommand

			outBuffer = new TemporaryBuffer.LocalFile(repository.getDirectory());
			errorBuffer = new TemporaryBuffer.Heap(1024
			rc = repository.getFS().runProcess(filterProcessBuilder
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
			if (errorBuffer != null)
				try {
					errorBuffer.close();
				} catch (IOException e) {
					throw new FilterFailedException(e
							outBuffer
				}
		}
	}
