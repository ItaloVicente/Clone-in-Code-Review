		LfsPointer ptr = LfsPointer.parseLfsPointer(in);
		if (ptr != null) {
			Path mediaFile = lfsUtil.getMediaFile(ptr.getOid());
			if (!Files.exists(mediaFile)) {
				downloadLfsObject(db
			}
			mIn = Files.newInputStream(mediaFile);
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
					lfsUtil.getMediaFile(longObjectId)
				byte buffer[] = new byte[4096];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					aoos.write(buffer
				}
