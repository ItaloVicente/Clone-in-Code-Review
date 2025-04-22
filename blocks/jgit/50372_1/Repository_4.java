
	public LocalFile runFilter(final String path
			Map<String
		LocalFile outBuffer = null;
		Attribute filter = attributes.get(Constants.ATTR_FILTER);
		if (filter != null) {
			String filterCommand = getConfig().getString(Constants.ATTR_FILTER
					filter.getValue()
			if (filterCommand != null) {
				filterCommand = filterCommand.replaceAll("%f"
				ProcessBuilder filterProcessBuilder = FS.DETECTED.runInShell(
						filterCommand

				outBuffer = new LocalFile(getDirectory());
				LocalFile errorBuffer = new LocalFile(getDirectory());
				int rc;
				try {
					rc = FS.DETECTED.runProcess(filterProcessBuilder
							outBuffer
							true);
				} catch (IOException | InterruptedException e) {
					throw new FilterFailedException(e
							outBuffer
				}
				if (rc != 0) {
					throw new FilterFailedException(rc
							outBuffer
				}
			}
		}
		return outBuffer;
	}
