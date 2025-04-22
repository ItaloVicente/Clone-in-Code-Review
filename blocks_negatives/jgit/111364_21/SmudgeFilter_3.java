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

	/**
	 * Determine URL of LFS server by looking into config parameters lfs.url,
	 * lfs.<remote>.url or remote.<remote>.url. The LFS server URL is computed
	 * from remote.<remote>.url by appending "/info/lfs"
	 *
	 * @param db
	 *            the repository to work with
	 * @param method
	 *            the method (GET,PUT,...) of the request this connection will
	 *            be used for
	 * @return the url for the lfs server. e.g.
	 * @throws IOException
	 */
	private HttpConnection getLfsConnection(Repository db, String method)
			throws IOException {
		StoredConfig config = db.getConfig();
		String lfsEndpoint = config.getString(Constants.LFS, null,
				ConfigConstants.CONFIG_KEY_URL);
		Map<String, String> additionalHeaders = new TreeMap<>();
		if (lfsEndpoint == null) {
			String remoteUrl = null;
			for (String remote : db.getRemoteNames()) {
				lfsEndpoint = config.getString(Constants.LFS, remote,
						ConfigConstants.CONFIG_KEY_URL);
				if (lfsEndpoint == null
						&& (remote.equals(
								org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME))) {
					remoteUrl = config.getString(
							ConfigConstants.CONFIG_KEY_REMOTE, remote,
							ConfigConstants.CONFIG_KEY_URL);
				}
				break;
			}
			if (lfsEndpoint == null && remoteUrl != null) {
				try {
					URIish u = new URIish(remoteUrl);

						String json = runSshCommand(u.setPath(""), db.getFS(), //$NON-NLS-1$

						Protocol.Action action = new Gson().fromJson(json,
								Protocol.Action.class);
						additionalHeaders.putAll(action.header);
						lfsEndpoint = action.href;
					} else {
						lfsEndpoint = remoteUrl + Protocol.INFO_LFS_ENDPOINT;
					}
				} catch (Exception e) {
				}
			} else {
				lfsEndpoint = lfsEndpoint + Protocol.INFO_LFS_ENDPOINT;
			}
		}
		if (lfsEndpoint == null) {
			throw new IOException(LfsText.get().lfsNeedDownloadNoUrl);
		}
		URL url = new URL(lfsEndpoint + Protocol.OBJECTS_LFS_ENDPOINT);
		HttpConnection connection = HttpTransport.getConnectionFactory().create(
				url, HttpSupport.proxyFor(ProxySelector.getDefault(), url));
		connection.setDoOutput(true);
				&& !config.getBoolean(HttpConfig.HTTP,
						HttpConfig.SSL_VERIFY_KEY, true)) {
			HttpSupport.disableSslVerify(connection);
		}
		connection.setRequestMethod(method);
		connection.setRequestProperty(HDR_ACCEPT,
				Protocol.CONTENTTYPE_VND_GIT_LFS_JSON);
		connection.setRequestProperty(HDR_CONTENT_TYPE,
				Protocol.CONTENTTYPE_VND_GIT_LFS_JSON);
		additionalHeaders
				.forEach((k, v) -> connection.setRequestProperty(k, v));
		return connection;
	}

	private String extractProjectName(URIish u) {
		String path = u.getPath().substring(1);
		if (path.endsWith(org.eclipse.jgit.lib.Constants.DOT_GIT)) {
			return path.substring(0, path.length() - 4);
		} else {
			return path;
		}
	}

	private String runSshCommand(URIish sshUri, FS fs, String command)
			throws IOException {
		RemoteSession session = null;
		Process process = null;
		StreamCopyThread errorThread = null;
		try (MessageWriter stderr = new MessageWriter()) {
			session = SshSessionFactory.getInstance().getSession(sshUri,
					null, fs, 5_000);
			process = session.exec(command, 0);
			errorThread = new StreamCopyThread(process.getErrorStream(),
					stderr.getRawStream());
			errorThread.start();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream(),
							org.eclipse.jgit.lib.Constants.CHARSET))) {
				return reader.readLine();
			}
		} finally {
			if (process != null) {
				process.destroy();
			}
			if (errorThread != null) {
				try {
					errorThread.halt();
				} catch (InterruptedException e) {
				} finally {
					errorThread = null;
				}
			}
			if (session != null) {
				SshSessionFactory.getInstance().releaseSession(session);
			}
		}
	}

