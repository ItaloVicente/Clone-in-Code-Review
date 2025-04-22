	private static final List<WeakReference<TransportProtocol>> protocols =
		new CopyOnWriteArrayList<WeakReference<TransportProtocol>>();

	static {
		register(TransportLocal.PROTO_LOCAL);
		register(TransportBundleFile.PROTO_BUNDLE);
		register(TransportAmazonS3.PROTO_S3);
		register(TransportGitAnon.PROTO_GIT);
		register(TransportSftp.PROTO_SFTP);
		register(TransportHttp.PROTO_FTP);
		register(TransportHttp.PROTO_HTTP);
		register(TransportGitSsh.PROTO_SSH);
	}

	public static void register(TransportProtocol proto) {
		protocols.add(0
	}

	public static void unregister(TransportProtocol proto) {
		for (WeakReference<TransportProtocol> ref : protocols) {
			TransportProtocol refProto = ref.get();
			if (refProto == null || refProto == proto)
				protocols.remove(ref);
		}
	}

	public static List<TransportProtocol> getTransportProtocols() {
		int cnt = protocols.size();
		List<TransportProtocol> res = new ArrayList<TransportProtocol>(cnt);
		for (WeakReference<TransportProtocol> ref : protocols) {
			TransportProtocol proto = ref.get();
			if (proto != null)
				res.add(proto);
			else
				protocols.remove(ref);
		}
		return Collections.unmodifiableList(res);
	}

