		} catch (IOException e) {
			throw new LfsTransportException(e.getMessage()
		}
	}

	private HttpConnection getLfsServerConnection(Repository db
			LongObjectId loid)
			throws IOException {
		Config config = db.getConfig();
		String lfsUrl = config.getString("lfs"
		if (lfsUrl == null) {
			return null;
