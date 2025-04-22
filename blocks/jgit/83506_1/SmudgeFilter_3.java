			LongObjectId oid = res.getOid();
			Path mediaFile = lfs.getMediaFile(oid);
			if (!Files.exists(mediaFile)) {
				downloadLfsResource(db

			}
			this.in = Files.newInputStream(mediaFile);
		}
	}

	@SuppressWarnings("boxing")
	private Set<Path> downloadLfsResource(Repository db
			throws IOException {
		Set<Path> downloadedPathes = new HashSet<Path>();
		Map<String
		for(LfsPointer p: res) {
			oidStr2ptr.put(p.getOid().name()
		}
		HttpConnection lfsServerConn = getLfsConnection(db
				HttpSupport.METHOD_POST);
		Gson gson = new Gson();
		lfsServerConn.getOutputStream()
				.write(gson.toJson(body(res)).getBytes(StandardCharsets.UTF_8));
		int responseCode = lfsServerConn.getResponseCode();
		if (responseCode != 200) {
			throw new IOException(MessageFormat
					.format(LfsText.get().serverFailure
							responseCode));
		}
		try (JsonReader reader = new JsonReader(
				new InputStreamReader(lfsServerConn.getInputStream()))) {
			Protocol.Response resp = gson.fromJson(reader
			for (Protocol.ObjectInfo o : resp.objects) {
				if (o.actions == null) {
					continue;
				}
				LfsPointer ptr = oidStr2ptr.get(o.oid);
				if (ptr == null) {
					continue;
				}
				if (ptr.getSize() != o.size) {
					throw new IOException(MessageFormat.format(
							LfsText.get().inconsistentContentLength
							lfsServerConn.getURL()
							o.size));
				}
				Protocol.Action downloadAction = o.actions
						.get(Protocol.OPERATION_DOWNLOAD);
				if (downloadAction == null || downloadAction.href == null) {
					continue;
				}
				URL contentUrl = new URL(downloadAction.href);
				HttpConnection contentServerConn = HttpTransport
						.getConnectionFactory().create(contentUrl
								HttpSupport.proxyFor(ProxySelector.getDefault()
										contentUrl));
				contentServerConn.setRequestMethod(HttpSupport.METHOD_GET);
						.getBoolean("http"
					HttpSupport.disableSslVerify(contentServerConn);
				}
				contentServerConn.setRequestProperty(HDR_ACCEPT_ENCODING
						ENCODING_GZIP);
				responseCode = contentServerConn.getResponseCode();
				if (responseCode != HttpConnection.HTTP_OK) {
					throw new IOException(
							MessageFormat.format(LfsText.get().serverFailure
									contentServerConn.getURL()
				}
				Path path = lfs.getMediaFile(ptr.getOid());
				path.getParent().toFile().mkdirs();
				try (InputStream contentIn = contentServerConn
						.getInputStream()) {
					long bytesCopied = Files.copy(contentIn
					if (bytesCopied != o.size) {
						throw new IOException(MessageFormat.format(
								LfsText.get().wrongAmoutOfDataReceived
								contentServerConn.getURL()
								o.size));
					}
					downloadedPathes.add(path);
				}
