			AnyLongObjectId oid = res.getOid();
			Path mediaFile = lfs.getMediaFile(oid);
			if (!Files.exists(mediaFile)) {
				downloadLfsResource(db

			}
			this.in = Files.newInputStream(mediaFile);
		}
	}

	private Set<Path> downloadLfsResource(Repository db
			throws IOException {
		Set<Path> downloadedPathes = new HashSet<>();
		Map<String
		for (LfsPointer p : res) {
			oidStr2ptr.put(p.getOid().name()
		}
		HttpConnection lfsServerConn = getLfsConnection(db
				HttpSupport.METHOD_POST);
		Gson gson = new Gson();
		lfsServerConn.getOutputStream()
				.write(gson.toJson(body(res)).getBytes(StandardCharsets.UTF_8));
		int responseCode = lfsServerConn.getResponseCode();
		if (responseCode != 200) {
			throw new IOException(
					MessageFormat.format(LfsText.get().serverFailure
							lfsServerConn.getURL()
							Integer.valueOf(responseCode)));
		}
		try (JsonReader reader = new JsonReader(
				new InputStreamReader(lfsServerConn.getInputStream()))) {
			Protocol.Response resp = gson.fromJson(reader
					Protocol.Response.class);
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
							Long.valueOf(o.size)));
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
				downloadAction.header.forEach(
						(k
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
									Integer.valueOf(responseCode)));
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
								Long.valueOf(bytesCopied)
								Long.valueOf(o.size)));
					}
					downloadedPathes.add(path);
				}
			}
		}
		return downloadedPathes;
	}

	private Protocol.Request body(LfsPointer... resources) {
		Protocol.Request req = new Protocol.Request();
		req.operation = Protocol.OPERATION_DOWNLOAD;
		if (resources != null) {
			req.objects = new LinkedList<>();
			for (LfsPointer res : resources) {
				Protocol.ObjectSpec o = new Protocol.ObjectSpec();
				o.oid = res.getOid().getName();
				o.size = res.getSize();
				req.objects.add(o);
			}
		}
		return req;
	}

	private HttpConnection getLfsConnection(Repository db
			throws IOException {
		StoredConfig config = db.getConfig();
		String lfsEndpoint = config.getString("lfs"
		Map<String
		if (lfsEndpoint == null) {
			String remoteUrl = null;
			for (String remote : db.getRemoteNames()) {
				lfsEndpoint = config.getString("lfs"
				if (lfsEndpoint == null
					remoteUrl = config.getString("remote"
				}
				break;
			}
			if (lfsEndpoint == null && remoteUrl != null) {
				try {
					URIish u = new URIish(remoteUrl);

						String json = runSshCommand(u.setPath("")

						Protocol.Action action = new Gson().fromJson(json
								Protocol.Action.class);
						additionalHeaders.putAll(action.header);
						lfsEndpoint = action.href;
					} else {
					}
				} catch (Exception e) {
				}
			} else {
			}
		}
		if (lfsEndpoint == null) {
			throw new IOException(
		}
		HttpConnection connection = HttpTransport.getConnectionFactory().create(
				url
		connection.setDoOutput(true);
				&& !config.getBoolean("http"
			HttpSupport.disableSslVerify(connection);
		}
		connection.setRequestMethod(method);
		connection.setRequestProperty(HDR_ACCEPT
				Protocol.CONTENTTYPE_VND_GIT_LFS_JSON);
		connection.setRequestProperty(HDR_CONTENT_TYPE
				Protocol.CONTENTTYPE_VND_GIT_LFS_JSON);
		additionalHeaders
				.forEach((k
		return connection;
	}

	private String extractProjectName(URIish u) {
		String path = u.getPath().substring(1);
			return path.substring(0
		} else {
			return path;
		}
	}

	private String runSshCommand(URIish sshUri
			throws IOException {
		RemoteSession session = null;
		Process process = null;
		StreamCopyThread errorThread = null;
		try (MessageWriter stderr = new MessageWriter()) {
			session = SshSessionFactory.getInstance().getSession(sshUri
					null
			process = session.exec(command
			errorThread = new StreamCopyThread(process.getErrorStream()
					stderr.getRawStream());
			errorThread.start();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()
							Constants.CHARSET))) {
				return reader.readLine();
			}
		} finally {
			if (errorThread != null) {
				try {
					errorThread.halt();
				} catch (InterruptedException e) {
				} finally {
					errorThread = null;
				}
			}
			if (process != null) {
				process.destroy();
			}
			if (session != null) {
				SshSessionFactory.getInstance().releaseSession(session);
