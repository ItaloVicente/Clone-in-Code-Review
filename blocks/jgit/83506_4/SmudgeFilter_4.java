		return downloadedPathes;
	}

	private Protocol.Request body(LfsPointer... resources) {
		Protocol.Request req = new Protocol.Request();
		req.operation = Protocol.OPERATION_DOWNLOAD;
		if (resources != null) {
			req.objects = new LinkedList<Protocol.ObjectSpec>();
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
				lfsEndpoint = remoteUrl;
			}
		}
		if (lfsEndpoint == null) {
			throw new IOException(
		}
		HttpConnection connection = HttpTransport.getConnectionFactory().create(
				url
		connection.setDoOutput(true);
		if (url.getProtocol().equals("https")
				&& !config.getBoolean("http"
			HttpSupport.disableSslVerify(connection);
		}
		connection.setRequestMethod(method);
		connection.setRequestProperty(HDR_ACCEPT
				Protocol.CONTENTTYPE_VND_GIT_LFS_JSON);
		connection.setRequestProperty(HDR_CONTENT_TYPE
				Protocol.CONTENTTYPE_VND_GIT_LFS_JSON);
		return connection;
