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
		String lfsEndpoint = config.getString("lfs", null, "url"); //$NON-NLS-1$ //$NON-NLS-2$
		Map<String, String> additionalHeaders = new TreeMap<>();
		if (lfsEndpoint == null) {
			String remoteUrl = null;
			for (String remote : db.getRemoteNames()) {
				lfsEndpoint = config.getString("lfs", remote, "url"); //$NON-NLS-1$ //$NON-NLS-2$
				if (lfsEndpoint == null
					remoteUrl = config.getString("remote", remote, "url"); //$NON-NLS-1$ //$NON-NLS-2$
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
				url, HttpSupport.proxyFor(ProxySelector.getDefault(), url));
		connection.setDoOutput(true);
				&& !config.getBoolean("http", "sslVerify", true)) { //$NON-NLS-1$ //$NON-NLS-2$
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
							Constants.CHARSET))) {
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

