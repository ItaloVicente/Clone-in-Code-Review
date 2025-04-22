
	public LocalFile processFilter(String filterCommandType
			throws FilterFailedException {
		LocalFile outBuffer = null;
		LocalFile errorBuffer = null;
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
			if ((filter = attributes.get(Constants.ATTR_FILTER)) == null) {
				return null;
			}
			if ((filterCommand = config.getString(Constants.ATTR_FILTER
					filter.getValue()
							filterCommandType)) == null) {
				return null;
			}
			filterCommand = filterCommand.replaceAll("%f"
			ProcessBuilder filterProcessBuilder = FS.DETECTED.runInShell(
					filterCommand

			outBuffer = new LocalFile(repository.getDirectory());
			errorBuffer = new LocalFile(repository.getDirectory());
			rc = FS.DETECTED.runProcess(filterProcessBuilder
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
