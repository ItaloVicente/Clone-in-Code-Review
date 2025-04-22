			this.in = Files.newInputStream(mediaFile);
		}
	}

	private void downloadLfsObject(Repository db
			throws LfsTransportException {
		HttpConnection connection;
		try {
			connection = getLfsServerConnection(db
			connection.setRequestMethod(HttpSupport.METHOD_GET);
			int rc = connection.getResponseCode();
			if (rc != HttpURLConnection.HTTP_OK) {
			}
			InputStream inputStream = connection.getInputStream();

			try (AtomicObjectOutputStream aoos = new AtomicObjectOutputStream(
					lfs.getMediaFile(longObjectId)
				byte buffer[] = new byte[4096];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					aoos.write(buffer
				}
			}
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
