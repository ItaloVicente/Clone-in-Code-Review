	private final Daemon daemon;

	private InetAddress peer;

	private InputStream rawIn;

	private OutputStream rawOut;

	DaemonClient(final Daemon d) {
		daemon = d;
	}

	void setRemoteAddress(final InetAddress ia) {
		peer = ia;
	}

	public Daemon getDaemon() {
		return daemon;
	}

	public InetAddress getRemoteAddress() {
		return peer;
	}

	public InputStream getInputStream() {
		return rawIn;
	}

	public OutputStream getOutputStream() {
		return rawOut;
	}

	void execute(final Socket sock) throws IOException
		rawIn = new BufferedInputStream(sock.getInputStream());
		rawOut = new BufferedOutputStream(sock.getOutputStream());

		if (0 < daemon.getTimeout()) {
			sock.setSoTimeout(daemon.getTimeout() * 1000);
		}
		String cmd = new PacketLineIn(rawIn).readStringRaw();
		final int nul = cmd.indexOf('\0');
		if (nul >= 0) {
			cmd = cmd.substring(0
		}

		final DaemonService srv = getDaemon().matchService(cmd);
		if (srv == null) {
			return;
		}
		sock.setSoTimeout(0);
		srv.execute(this
	}
